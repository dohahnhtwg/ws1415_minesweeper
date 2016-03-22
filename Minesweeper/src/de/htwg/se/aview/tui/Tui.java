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

public class Tui implements IObserver {

    private IController controller;
    private static final Logger LOGGER = Logger.getLogger("aview.TextGUI");
    private IHandler handlerNew;

    @Inject
    public Tui(IController controller)   {
        this.controller = controller;
        controller.addObserver(this);
        IHandler handlerSize;
        IHandler handlerUnReDo;
        IHandler handlerInput;
        handlerNew = new ConcreteHandlerNew();
        handlerSize = new ConcreteHandlerSize();
        handlerUnReDo = new ConcreteHandlerUnReDo();
        handlerInput = new ConcreteHandlerInput();
        handlerNew.setSuccesor(handlerSize);
        handlerSize.setSuccesor(handlerUnReDo);
        handlerUnReDo.setSuccesor(handlerInput);
        handlerInput.setSuccesor(null);
        
    }

    public void paintTUI() {
        LOGGER.info(controller.getField());
        LOGGER.info("Possible commands: q = quit, n = new Game, sS = small Field, sM = medium Field, sL = Large Field, yy-xx = reveal Field, u = undo, r = redo");
    }

    public boolean processInputLine(String next) {
        boolean proceed = true;
        
        if("q".equals(next)) {
        	controller.finishGame();
            proceed = false;
        } else if(!handlerNew.handleRequest(next, controller))  {
            LOGGER.info("illegal argument");
        }
        return proceed;
    }

    @Override
    public void update(Event e) {
        paintTUI();
        if(controller.isGameOver())  {
            LOGGER.info("GAME OVER!!!");
        }
        if(controller.isVictory())   {
            LOGGER.info("Victory!!!");
        }
    }
    
    public IController getController() {
    	return this.controller;
    }

    public IHandler getChainOfResponsibility()    {
        return handlerNew;
    }
}
