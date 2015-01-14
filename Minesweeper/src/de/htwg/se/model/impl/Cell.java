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

package de.htwg.se.model.impl;

import de.htwg.se.model.ICell;


public class Cell implements ICell {

    private int value;
    private boolean isRevealed = false;

    public Cell(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
    
    public String toString()    {
        if(isRevealed())   {
            if(value == -1)   {
                return String.format(" %2c ", '*');
            } else {
                return String.format(" %2s ", value);
            }
        } else {
            return String.format(" %2c ", '-');
        }
    }

    @Override
    public boolean equals(Object obj) {
        Cell other = (Cell) obj;
        if (obj == null || getClass() != obj.getClass() || isRevealed != other.isRevealed || value != other.value) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value) + Boolean.hashCode(isRevealed);
    }
}
