package de.htwg.se.controller.impl;

import java.awt.Point;
import java.util.ArrayList;
//import java.util.Observable;
import java.util.Random;

import de.htwg.se.controller.IField;
import de.htwg.se.model.impl.Cell;
import de.htwg.se.util.observer.Observable;

public class Field extends Observable implements IField {

    private Cell[][] playingField;
    private int nMines;
    private int lines;
    private int columns;
    private boolean gameOver = false;
    private boolean victory = false;

    public Field(int x, int y, int nMines)  {
        //super();
        create(x, y, nMines);
    }

    public void create(int x, int y, int nMines)    {
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

    public int getnMines() {
        return nMines;
    }

    public boolean isVictory() {
        return victory;
    }

    public Cell[][] getField() {
        return playingField;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void revealField(int x, int y) {
        if(playingField[x][y].getValue() == -1) {
            playingField[x][y].setRevealed(true);
            gameOver = true;
        } else {
            revealFieldHelp(x, y);
            victory = checkVictory();
        }
        //super.setChanged();
        //super.notifyObservers();
        notifyObservers();
    }
    
    private void revealFieldHelp(int x, int y)  {
        if(playingField[x][y].getValue() > 0)  {
            playingField[x][y].setRevealed(true);
            return;
        } else {
            playingField[x][y].setRevealed(true);
            ArrayList<Point> fieldsaround = getFieldsAround(x, y);
            for(Point field : fieldsaround) {
                if((field.getX() > 0 && field.getY() > 0) && (field.getX() < playingField.length-1 && field.getY() < playingField[(int)field.getX()].length-1))    {
                    if(!playingField[(int)field.getX()][(int)field.getY()].isRevealed())    {
                        revealFieldHelp(field.x, field.y);
                    }
                }
            }
            return;
        }
    }

    private ArrayList<Point> getFieldsAround(int x, int y) {
        ArrayList<Point> fieldsAround = new ArrayList<Point>();
        fieldsAround.add(new Point(x - 1, y));
        fieldsAround.add(new Point(x + 1, y));
        fieldsAround.add(new Point(x - 1, y - 1));
        fieldsAround.add(new Point(x - 1, y + 1));
        fieldsAround.add(new Point(x + 1, y + 1));
        fieldsAround.add(new Point(x + 1, y - 1));
        fieldsAround.add(new Point(x , y - 1));
        fieldsAround.add(new Point(x , y + 1));
        return fieldsAround;
    }
    
    private boolean checkVictory()  {
        int requirement = lines * columns - nMines;
        int current = 0;
        
        for (int i = 0; i < playingField.length; i++)   {
            for (int j = 0; j < playingField[0].length; j ++)    {
                if(playingField[i][j].isRevealed()) {
                    current++;
                }
            }
        }
        
        if(current == requirement)  {
            return true;
        }
        return false; 
    }

}
