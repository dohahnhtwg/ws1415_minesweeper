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

package de.htwg.se.model;

public interface ICell {

    /**
     * @return the Value of the Cell. -1 stands for a Mine and 0-6 for the 
     *         number of Mines around.
     */
    int getValue();
    
    /** 
     * @param newValue is the new Value. -1 stands for a Mine and 0-6 for the 
     *        number of Mines around.
     */
    void setValue(int newValue);
    
    /**
     * @return true if the Cell is Revealed.
     */
    boolean isRevealed();
    
    /**
     * @param isRevealed
     */
    void setRevealed(boolean isRevealed);
    
    /**
     * @return String representing a Cell.
     */
    String toString();
    
    /**
     * @return true if given Cells are equals.
     */
    boolean equals(Object obj);
}
