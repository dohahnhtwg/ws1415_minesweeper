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
        if (this == obj) {
            return true;
        }
        Cell other = (Cell) obj;
        if (obj == null || getClass() != obj.getClass() || isRevealed != other.isRevealed || value != other.value) {
            return false;
        }
        return true;
    }
    
}
