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

/**
 * This interface represent a playing field build of Cells.
 * @author Dominik Hahn & Pavel Kravetskiy
 *
 */
public interface IField {

    /**
     * Creates a new playing Field
     * @param x is the number of lines.
     * @param y is the number of columns.
     * @param nMines is the number of Mines in the playing Field.
     */
    void create(int x, int y, int nMines);
    
    /**
     * This Method returns the number of lines in the actual playing field.
     * @return number of lines.
     */
    int getLines();

    /**
     * Set number of lines in the field
     * @param lines int
     */
    void setLines(int lines);

    /**
     * This Method returns the number of columns in the actual playing field.
     * @return number of columns.
     */
    int getColumns();

    /**
     * Set number of columns in the field
     * @param columns int
     */
    void setColumns(int columns);
    
    /**
     * This Method returns the number of mines in the actual playing field.
     * @return number of Mines.
     */
    int getnMines();

    /**
     * Set number of mines in the field
     * @param nMines int
     */
    void setnMines(int nMines);

    /**
     * This Method returns the actual playing field.
     * @return whole playing Field.
     */
    ICell[][] getField();
    
    /**
     * This Method returns the actual playing field as formated String.
     * @return String representing a Field.
     */
    String toString();

    /**
     * Get field id
     * @return id
     */
    String getFieldID();

    /**
     * Set field id
     * @param fieldID id
     */
    void setFieldID(String fieldID);

    /**
     * Get playing field
     * @return playing field
     */
    ICell[][] getPlayingField();

    /**
     * Set playing field
     * @param playingField field
     */
    void setPlayingField(ICell[][] playingField);

}
