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

import de.htwg.se.model.IField;
import de.htwg.se.util.observer.IObservable;

/**
 * This interface represent a Controller for the Minesweeper Game.
 * @author Dominik Hahn & Pavel Kravetskiy
 *
 */
public interface IController extends IObservable {

    /**
     * Creates a playing Field with the same parameters as old ones.
     */
    void create();
    
    /**
     * Creates a new playing Field.
     * @param lines number of lines.
     * @param columns number of columns.
     * @param nMines number of Mines.
     */
    void create(int lines, int columns, int nMines);
    
    /**
     * @return true if the Player is victorious.
     */
    boolean isVictory();
    
    /**
     * @return true if the Player lost the game.
     */
    boolean isGameOver();
    
    /**
     * reveal the Field with the given Coordinates.
     * @param x is the x-Coordinate.
     * @param y is the y-Coordinate.
     */
    void revealField(int x, int y);
    
    /**
     * undo the last revealeFieldCommand.
     */
    void undo();
    
    /**
     * redo the last undo.
     */
    void redo();
    
    /**
     * @return the painting of a Field.
     */
    String getField();
    
    /**
     * Returns the actual playing field of the controller.
     * @return the actual playing Field.
     */
    IField getPlayingField();

    /**
     * @return the number of victories.
     */
    int getVictories();

    /**
     * 
     * @return the number of loses.
     */
    int getLoses();
}
