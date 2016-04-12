package de.htwg.se.database.couchDB;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/**
 * Created by GAAB on 11.04.2016.
 *
 */
public class CouchCell extends CouchDbDocument {

    @TypeDiscriminator
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
