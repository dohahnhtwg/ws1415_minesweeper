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

import java.util.UUID;


public class Cell implements ICell {

    private String id;
    private int value;
    private boolean isRevealed = false;
    private String playingFieldId;


    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public Cell(int value) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
    }

    public Cell(String id, Integer value, Boolean isRevealed, String playingFieldId) {
        this.id = id;
        this.value = value;
        this.isRevealed = isRevealed;
        this.playingFieldId = playingFieldId;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public String toString() {
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        return isRevealed == other.isRevealed && value == other.value;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + value;
        result = 31 * result + (isRevealed ? 1 : 0);
        return result;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isRevealed() {
        return this.isRevealed;
    }

    @Override
    public void setIsRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
}
