package de.htwg.se.database.couchDB;

/**
 * Created by GAAB on 11.04.2016.
 *
 */
public class CouchCell {

    private int value;
    private boolean isRevealed = false;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }
}
