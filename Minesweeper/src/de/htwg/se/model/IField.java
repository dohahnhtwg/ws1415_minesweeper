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

public interface IField {

    /**
     * Creates a new playing Field
     * @param x is the number of lines.
     * @param y is the number of columns.
     * @param nMines is the number of Mines in the playing Field.
     */
    void create(int x, int y, int nMines);
    
    /**
     * @return number of lines.
     */
    int getLines();
    
    /**
     * @return number of columns.
     */
    int getColumns();
    
    /**
     * @return number of Mines.
     */
    int getnMines();
    
    /**
     * @return whole playing Field.
     */
    ICell[][] getField();
    
    /**
     * @return String representing a Field.
     */
    String toString();
    
    
}
