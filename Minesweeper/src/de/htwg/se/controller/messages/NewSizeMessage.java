package de.htwg.se.controller.messages;

import java.io.Serializable;

/**
 * Created by GAAB on 21.05.2016.
 */
public class NewSizeMessage implements Serializable {
    private Integer lines;
    private Integer columns;
    private Integer mines;

    public NewSizeMessage(Integer lines, Integer columns, Integer mines)    {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;
    }

    public Integer getLines()   {
        return lines;
    }

    public Integer getColumns() {
        return columns;
    }

    public Integer getMines()   { return mines; }
}
