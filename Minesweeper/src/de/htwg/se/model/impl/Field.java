package de.htwg.se.model.impl;

import java.util.Random;

import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;

public class Field implements IField{

    private ICell[][] playingField;
    private int nMines;
    private int lines;
    private int columns;
    
    public Field()  {
        create(9, 9, 10);
    }
    
    public Field(int lines, int columns, int nMines)  {
        create(lines, columns, nMines);
    }
    
    public void create(int lines, int columns, int nMines)    {
        this.lines = lines;
        this.columns = columns;
        this.nMines = nMines;
        if (lines < 1 || columns < 1 || nMines < 0)   {
            throw new IllegalArgumentException("lines, columns or nMines too small.");
        }
        playingField = new ICell[lines + 2][columns + 2];
        for (int i = 0; i < playingField.length; i++)   {
            for (int j = 0; j < playingField[0].length; j ++)    {
                playingField[i][j] = new Cell(0);
            }
        }
        fill(lines, columns);
    }
    
    private void fill(int lines, int columns) {
        Long timeforhash = System.nanoTime();
        int hash = timeforhash.hashCode();
        Random rnd = new Random(hash);
        for (int i = 0; i < nMines; i++)    {
            generateMines(lines, columns, rnd);
        }
        generateMinesAround();
    }
    
    private void generateMines(int lines, int columns, Random rnd)    {
        int x = rnd.nextInt(lines /*- 1*/) + 1;
        int y = rnd.nextInt(columns /*- 1*/) + 1;
        if (playingField[x][y].getValue() != -1)    {
            playingField[x][y].setValue(-1);
        } else  {
            generateMines(lines, columns, rnd);
        }
    }
    
    private void generateMinesAround() {
        for (int i = 1; i < playingField.length - 1; i++)   {
            for (int j = 1; j < playingField[0].length - 1; j ++)   {
                if (playingField[i][j].getValue() != -1)    {
                    playingField[i][j].setValue(nMinesAroundAPoint(i, j));
                }
            }
        }
    }
    
    private int nMinesAroundAPoint(int x, int y)    {
        int mines = 0;
        int smallFieldLen = 3; 
        for (int i = 0; i < smallFieldLen; i++) {
            for (int j = 0; j < smallFieldLen; j++) {
                if (playingField[x - 1 + i][y - 1 + j].getValue() == -1)    {
                    mines++;
                }
            }
        }
        return mines;
    }
    
    public String toString()    {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(String.format("%5s\n", "Line"));
        for (int i = 1; i <= lines; i++) {
            sb.append(String.format("%5d", i));
            for (int j = 1; j <= columns; j++)  {
                sb.append(playingField[i][j].toString());
            }
            sb.append("\n");
        }
        sb.append("     ");
        for (int j = 1; j <= columns; j++)  {
            sb.append(String.format(" %2d ", j));
        }
        sb.append(String.format("\n       %s", "Column\n"));
        return sb.toString();
    }
    
    public int getLines()   {
        return lines;
    }


    public int getColumns() {
        return columns;
    }

    public int getnMines() {
        return nMines;
    }
    
    public ICell[][] getField() {
        return playingField;
    }
}
