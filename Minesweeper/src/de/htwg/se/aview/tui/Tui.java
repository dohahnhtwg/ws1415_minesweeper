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

import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.google.inject.Inject;
import de.htwg.se.aview.tui.messages.*;
import de.htwg.se.aview.tui.messages.StatisticResponse;
import de.htwg.se.controller.impl.*;
import de.htwg.se.controller.messages.MainController.*;
import de.htwg.se.minesweeper.messages.TerminateRequest;
import de.htwg.se.model.IStatistic;
import org.apache.log4j.Logger;

public class Tui extends UntypedActor {

    private ActorRef controller;
    private static final Logger LOGGER = Logger.getLogger("aview.TextGUI");
    private IHandler handlerNew;
    private boolean loggedIn;

    @Inject
    public Tui()   {
        this.controller = getContext().actorOf(Props.create(MainController.class), "controller");
        createChainOfResponsibility();
        controller.tell(new UpdateRequest(), self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof InputMessage)    {
            String input = ((InputMessage) message).getInput();
            if(processInputLine(input)){return;}
            getContext().parent().tell(new TerminateRequest(), self());
            return;
        }
        if(message instanceof UpdateMessage)    {
            update((UpdateMessage)message);
            return;
        }
        if(message instanceof StatisticResponse)    {
            IStatistic statistic = ((StatisticResponse)message).getStatistic();
            printStatistic(statistic);
            return;
        }
        if(message instanceof NewAccountResponse)   {
            handleNewAccountResponse((NewAccountResponse)message);
        }
        if(message instanceof LoginResponse)    {
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

    /* public because of web */
    public boolean processInputLine(String next) {
        boolean proceed = true;
        
        if("q".equals(next)) {
        	controller.tell(new FinishGameMessage(), self());
            proceed = false;
        } else if("l".equals(next)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    startLoginSequence();
                }
            });
            thread.start();
        } else if ("s".equals(next)) {
            controller.tell(new StatisticRequest(), self());
        } else if(!handlerNew.handleRequest(next, controller, self()))  {
            LOGGER.info("illegal argument");
        }
        return proceed;
    }

    private void printStatistic(IStatistic s) {
        LOGGER.info("Number of all played games: " + s.getGamesPlayed());
        LOGGER.info("Number of won games: " + s.getGamesWon());
        LOGGER.info("Amount of played time: " + s.getTimeSpent());
        LOGGER.info("Minimal time spent for a game: " + s.getMinTime());
    }

    private void startLoginSequence() {
        Scanner s = new Scanner(System.in);
        loggedIn = false;
        while(!loggedIn)	{
            LOGGER.info("(1) Log in");
            LOGGER.info("(2) Create account");
            String input = s.next();
            if(input.equals("1"))	{
                tryToLogIn(s);
            } else {
                if(input.equals("2"))	{
                    startNewAccountSequence(s);
                } else {
                    LOGGER.info("Please enter 1 or 2");
                }
            }
        }
        s.close();
    }

    private void startNewAccountSequence(Scanner s) {
		LOGGER.info("Enter new username:");
		String username = s.next();
		LOGGER.info("Enter new password:");
		String password = s.next();
        controller.tell(new NewAccountRequest(username, password), self());
	}

	private void tryToLogIn(Scanner s) {
		LOGGER.info("Enter username:");
		String username = s.next();
		LOGGER.info("Enter password:");
		String password = s.next();
        controller.tell(new LoginRequest(username, password), self());
	}

    private void handleLoginResponse(LoginResponse loginResponse) {
        loggedIn = loginResponse.isSuccess();
        if(loggedIn)	{
            LOGGER.info("Successfully logged in");
        } else {
            LOGGER.info("Invalid");
        }
    }

    private void handleNewAccountResponse(NewAccountResponse response)  {
        boolean success = response.getSuccess();
        if(success)	{
            LOGGER.info("Successfully created new account");
        } else {
            LOGGER.info("Account already exists");
        }
    }
}
