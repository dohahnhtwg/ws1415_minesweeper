package de.htwg.se.database.couchDB;

/**
 * Created by GAAB on 11.04.2016.
 *
 */
public class CouchField {

    private CouchCell[][] playingField;
    private int nMines;
    private int lines;
    private int columns;

    public CouchCell[][] getPlayingField() {
        return playingField;
    }

    public void setPlayingField(CouchCell[][] playingField) {
        this.playingField = playingField;
    }

    public int getnMines() {
        return nMines;
    }

    public void setnMines(int nMines) {
        this.nMines = nMines;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
}
