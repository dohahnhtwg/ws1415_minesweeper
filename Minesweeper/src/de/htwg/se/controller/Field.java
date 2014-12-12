package de.htwg.se.controller;

import java.util.Observable;
import java.util.Random;

import de.htwg.se.model.Cell;

public class Field extends Observable {

    private Cell[][] playingField;
    private int nMines;
    private int lines;
    private int columns;

    public Field(int x, int y, int nMines)  {
        super();
        lines = x;
        columns = y;
        if (x < 1 || y < 1 || nMines < 0)   {
            return;
        }
        playingField = new Cell[x + 2][y + 2];
        for (int i = 0; i < playingField.length; i++)   {
            for (int j = 0; j < playingField[0].length; j ++)    {
                playingField[i][j] = new Cell(0);
            }
        }
        this.nMines = nMines;
        fill(x, y);
    }

    private void fill(int x, int y) {
        Long timeforhash = System.nanoTime();
        int hash = timeforhash.hashCode();
        Random rnd = new Random(hash);
        for (int k = 0; k < nMines; k++)    {
            generateMines(x, y, rnd);
        }
        generateMinesAround();
    }

    private void generateMines(int x, int y, Random rnd)    {
        while (true)    {
            int row = rnd.nextInt(x - 1) + 1;
            int col = rnd.nextInt(y - 1) + 1;
            if (playingField[row][col].getValue() != -1)    {
                playingField[row][col].setValue(-1);
                break;
            }
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


    public int getLines()   {
        return lines;
    }


    public int getColumns() {
        return columns;
    }

    public Cell[][] getField() {
        return playingField;
    }

    public void revealField(int x, int y) {
        playingField[x][y].setRevealed(true);
        super.setChanged();
        super.notifyObservers();
    }

}
