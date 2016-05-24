package de.htwg.se.controller.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 24.05.2016.
 */
public class CreateRequest implements Serializable {
    private final int lines;
    private final int columns;
    private final int nMines;

    public CreateRequest(int lines, int columns, int nMines) {

        this.lines = lines;
        this.columns = columns;
        this.nMines = nMines;
    }

    public int getnMines() {
        return nMines;
    }

    public int getColumns() {
        return columns;
    }

    public int getLines() {
        return lines;
    }
}
