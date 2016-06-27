/* This file is part of Minesweeper.
 * 
 * Minesweeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minesweeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minesweeper.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.htwg.se.aview.tui;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.inject.Inject;
import de.htwg.se.aview.gui.Constants;
import de.htwg.se.aview.messages.*;
import de.htwg.se.controller.messages.MainController.*;
import de.htwg.se.minesweeper.messages.TerminateRequest;
import de.htwg.se.model.IStatistic;
import org.apache.log4j.Logger;

import static de.htwg.se.aview.tui.LoginState.*;
import static de.htwg.se.aview.tui.LoginState.CREATE_ACCOUNT_SELECTED_USER;

public class Tui extends UntypedActor {

    private ActorRef controller;
    private static final Logger LOGGER = Logger.getLogger("aview.TextGUI");
    private IHandler handlerNew;
    private LoginState loginState;
    private String user = null;
    private boolean inLoginSequence = false;

    @Inject
    public Tui(final ActorRef controller)   {
        this.controller = controller;
        createChainOfResponsibility();
        controller.tell(new RegisterRequest(), self());
        loginState = LOGIN_SELECTED;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof InputMessage) {
            String input = ((InputMessage) message).getInput();
            if(processInputLine(input)){
                return;
            }
            getContext().parent().tell(new TerminateRequest(), self());
            return;
        }
        if(message instanceof UpdateMessage) {
            update((UpdateMessage)message);
            return;
        }
        if(message instanceof StatisticResponse) {
            IStatistic statistic = ((StatisticResponse)message).getStatistic();
            printStatistic(statistic);
            return;
        }
        if(message instanceof NewAccountResponse)   {
            handleNewAccountResponse((NewAccountResponse)message);
        }
        if(message instanceof LoginResponse) {
            handleLoginResponse((LoginResponse) message);
        }
        unhandled(message);
    }

    private void createChainOfResponsibility() {
        IHandler handlerSize = new de.htwg.se.aview.tui.handler.ConcreteHandlerSize();
        IHandler handlerUnReDo = new de.htwg.se.aview.tui.handler.ConcreteHandlerUnReDo();
        IHandler handlerInput = new de.htwg.se.aview.tui.handler.ConcreteHandlerInput();
        handlerNew = new de.htwg.se.aview.tui.handler.ConcreteHandlerNew();
        handlerNew.setSuccesor(handlerSize);
        handlerSize.setSuccesor(handlerUnReDo);
        handlerUnReDo.setSuccesor(handlerInput);
        handlerInput.setSuccesor(null);
    }

    private void update(UpdateMessage msg) {
        paintTUI(msg.getField().toString());
        if(msg.getField().isGameOver())  {
            LOGGER.info("GAME OVER!!!");
            LOGGER.info("Spent time: " + msg.getCurrentTime() + "s");
        }
        if(msg.getField().isVictory())   {
            LOGGER.info("Victory!!!");
        }
    }

    private void paintTUI(String field) {
        LOGGER.info(field);
        LOGGER.info("Possible commands: q = quit, n = new Game, sS = small Field, sM = medium Field, sL = Large Field, yy-xx = reveal Field, u = undo, r = redo, l = login, s = statistic");
    }

    public boolean processInputLine(String next) {
        boolean proceed = true;
        if(inLoginSequence)   {
            startLoginSequence(next);
            return true;
        }
        if("q".equals(next)) {
            controller.tell(new FinishGameMessage(), self());
            proceed = false;
        } else if("l".equals(next)) {
            inLoginSequence = true;
            LOGGER.info("(1) Log in");
            LOGGER.info("(2) Create account");
        } else if ("s".equals(next)) {
            controller.tell(new StatisticRequest(), self());
        } else if(!handlerNew.handleRequest(next, controller, self()))  {
            LOGGER.info("illegal argument '" + next + "'");
        }
        return proceed;
    }

    private void printStatistic(IStatistic s) {
        int games = s.getGamesPlayed();
        int wins = s.getGamesWon();
        LOGGER.info("Number of all played games: " + games);
        LOGGER.info("Number of won games: " + wins);
        LOGGER.info("Amount of played time: " + (s.getTimeSpent() == 0 ? "0" :
                String.valueOf(s.getTimeSpent() / 1000) + "s"));
        LOGGER.info("Minimal time spent for a game: " + (s.getMinTime() == Long.MAX_VALUE ? "-" :
                String.valueOf(s.getMinTime() / 1000) + "s"));
        LOGGER.info("Win percentage: " + (games != 0 ? (wins * Constants.DEF_BUT_SIZEX / games) : 0) + "%");
    }

    private void startLoginSequence(String input) {
        switch (loginState) {
            case LOGIN_SELECTED:
                switch (input) {
                    case "1":
                        loginState = LOG_IN_SELECTED_USER;
                        LOGGER.info("Enter username:");
                        break;
                    case "2":
                        loginState = CREATE_ACCOUNT_SELECTED_USER;
                        LOGGER.info("Enter new username:");
                        break;
                    default:
                        LOGGER.info("Please enter 1 or 2");
                        break;
                }
                break;
            case LOG_IN_SELECTED_USER:
                tryToLogIn(input);
                break;
            case LOG_IN_SELECTED_PASS:
                tryToLogIn(input);
                break;
            case CREATE_ACCOUNT_SELECTED_USER:
                startNewAccountSequence(input);
                break;
            case CREATE_ACCOUNT_SELECTED_PASS:
                startNewAccountSequence(input);
                break;
            default:
                throw new IllegalStateException("Your LoginSequence is confused, it hurt itself in confusion");
        }
    }

    private void startNewAccountSequence(String input) {
        switch(loginState)  {
            case CREATE_ACCOUNT_SELECTED_USER:
                user = input;
                loginState = CREATE_ACCOUNT_SELECTED_PASS;
                LOGGER.info("Enter new password:");
                break;
            case CREATE_ACCOUNT_SELECTED_PASS:
                loginState = LOGIN_SELECTED;
                inLoginSequence = false;
                controller.tell(new NewAccountRequest(user, input), self());
                break;
            default:
                throw new IllegalStateException("Your LoginSequence is confused, it hurt itself in confusion");
        }
    }

    private void tryToLogIn(String input) {
        switch(loginState)  {
            case LOG_IN_SELECTED_USER:
                user = input;
                loginState = LOG_IN_SELECTED_PASS;
                LOGGER.info("Enter password:");
                break;
            case LOG_IN_SELECTED_PASS:
                loginState = LOGIN_SELECTED;
                inLoginSequence = false;
                controller.tell(new LoginRequest(user, input), self());
                break;
            default:
                throw new IllegalStateException("Your LoginSequence is confused, it hurt itself in confusion");
        }
    }

    private void handleLoginResponse(LoginResponse loginResponse) {
        if(loginResponse.isSuccess()) {
            LOGGER.info("Successfully logged in");
        } else {
            LOGGER.info("Invalid");
        }
    }

    private void handleNewAccountResponse(NewAccountResponse response)  {
        boolean success = response.getSuccess();
        if(success) {
            LOGGER.info("Successfully created new account");
        } else {
            LOGGER.info("Account already exists");
        }
    }
}
