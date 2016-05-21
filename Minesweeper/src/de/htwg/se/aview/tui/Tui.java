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

import akka.actor.UntypedActor;
import de.htwg.se.aview.tui.messages.InputMessage;
import de.htwg.se.aview.tui.messages.PaintMessage;
import de.htwg.se.minesweeper.messages.TerminateMessage;
import de.htwg.se.model.IStatistic;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;
import de.htwg.se.controller.impl.ConcreteHandlerInput;
import de.htwg.se.controller.impl.ConcreteHandlerNew;
import de.htwg.se.controller.impl.ConcreteHandlerSize;
import de.htwg.se.controller.impl.ConcreteHandlerUnReDo;
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;

public class Tui extends UntypedActor implements IObserver {

    private IController controller;
    private static final Logger LOGGER = Logger.getLogger("aview.TextGUI");
    private IHandler handlerNew;
    private Scanner scanner;

    @Inject
    public Tui(IController controller)   {
        this.controller = controller;
        controller.addObserver(this);
        createChainOfResponsibility();
    }

    public void paintTUI() {
        LOGGER.info(controller.getField());
        LOGGER.info("Possible commands: q = quit, n = new Game, sS = small Field, sM = medium Field, sL = Large Field, yy-xx = reveal Field, u = undo, r = redo, l = login, s = statistic");
    }

    public boolean processInputLine(String next) {
        boolean proceed = true;
        
        if("q".equals(next)) {
        	controller.finishGame();
            proceed = false;
        } else if("l".equals(next)) {
            startLoginSequence();
        } else if ("s".equals(next)) {
            printStatistic();
        } else if(!handlerNew.handleRequest(next, controller))  {
            LOGGER.info("illegal argument");
        }
        return proceed;
    }

    public void update(Event e) {
        paintTUI();
        if(controller.isGameOver())  {
            LOGGER.info("GAME OVER!!!");
            LOGGER.info("Spent time: " + controller.getCurrentTime() + "s");
        }
        if(controller.isVictory())   {
            LOGGER.info("Victory!!!");
        }
    }
    
    public IController getController() {
    	return this.controller;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    private void printStatistic() {
        IStatistic s = controller.getUserStatistic();
        LOGGER.info("Number of all played games: " + s.getGamesPlayed());
        LOGGER.info("Number of won games: " + s.getGamesWon());
        LOGGER.info("Amount of played time: " + s.getTimeSpent());
        LOGGER.info("Minimal time spent for a game: " + s.getMinTime());
        paintTUI();
    }

    private void startLoginSequence() {
        boolean loggedIn = false;
        while(!loggedIn)	{
            LOGGER.info("(1) Log in");
            LOGGER.info("(2) Create account");
            String input = scanner.next();
            if(input.equals("1"))	{
                loggedIn = tryToLogIn();
                if(loggedIn)	{
                    LOGGER.info("Successfully logged in");
                } else {
                    LOGGER.info("Invalid");
                }
            } else {
                if(input.equals("2"))	{
                    startNewAccountSequence();
                } else {
                    LOGGER.info("Please enter 1 or 2");
                }
            }
        }
    }

    private void startNewAccountSequence() {
		LOGGER.info("Enter new username:");
		String username = scanner.next();
		LOGGER.info("Enter new password:");
		String password = scanner.next();
		boolean success = controller.addNewAccount(username, password);
		if(success)	{
			LOGGER.info("Successfully created new account");
		} else {
			LOGGER.info("Account already exists");
		}
	}

	private boolean tryToLogIn() {
		LOGGER.info("Enter username:");
		String username = scanner.next();
		LOGGER.info("Enter password:");
		String password = scanner.next();
		return controller.logIn(username, password);
	}

	private void createChainOfResponsibility() {
		IHandler handlerSize = new ConcreteHandlerSize();
        IHandler handlerUnReDo = new ConcreteHandlerUnReDo();
        IHandler handlerInput = new ConcreteHandlerInput();
        handlerNew = new ConcreteHandlerNew();
        handlerNew.setSuccesor(handlerSize);
        handlerSize.setSuccesor(handlerUnReDo);
        handlerUnReDo.setSuccesor(handlerInput);
        handlerInput.setSuccesor(null);
	}

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof InputMessage)    {
            String input = ((InputMessage) message).getInput();
            if(processInputLine(input)){return;}
            getContext().parent().tell(new TerminateMessage(), self());
            return;
        }
        if(message instanceof PaintMessage) {
            paintTUI();
            return;
        }
        unhandled(message);
    }
}
