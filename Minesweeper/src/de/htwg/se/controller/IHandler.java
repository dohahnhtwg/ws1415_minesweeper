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

package de.htwg.se.controller;

/**
 * This Interface represent the pattern of a Chain of Responsibility. 
 * @author Dominik Hahn & Pavel Kravetskiy
 *
 */
public interface IHandler {
    
    /**
     * Sets the next successor in a Chain of Responsibility
     * @param successor is the next Handler in the link.
     */
    void setSuccesor(IHandler successor);
    
    /**
     * This Method tries to handle the given Request. If this is not possible the
     * request is sent to the next link in the chain.
     * @param request is the String which should be handled by the actual link.
     * @param controller is the actual controller which this chain belongs.
     * @return returns true if the chain was able to handle the given request
     *         else false.
     */
    boolean handleRequest(String request, IController controller);
    
    IHandler getSuccesor();
    
}
