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

package de.htwg.se.controller.impl;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;

public class ConcreteHandlerUnReDo implements IHandler {

    private IHandler successor;
    
    @Override
    public void setSuccesor(IHandler successor) {
        this.successor = successor;
    }

    @Override
    public boolean handleRequest(String request, IController controller) {
        if(request.equals("u"))  {
            controller.undo();
            return true;
        }
        if(request.equals("r"))  {
            controller.redo();
            return true;
        }
        return successor.handleRequest(request, controller);
    }

    @Override
    public IHandler getSuccesor() {
        return successor;
    }

}
