package de.htwg.se.model.impl;

import de.htwg.se.model.ICell;


public class Cell implements ICell {
    private int value;
    private boolean isRevealed = false;

    public Cell(int value) {
        this.value = value;
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
}