package de.htwg.se.controller.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.undo.UndoManager;

import de.htwg.se.controller.IField;
import de.htwg.se.controller.revealFieldCommand;
import de.htwg.se.model.impl.Cell;
import de.htwg.se.model.ICell;
import de.htwg.se.util.observer.Observable;

public class Field extends Observable implements IField {

    private ICell[][] playingField;
    private int nMines;
    private int lines;
    private int columns;
    private boolean gameOver = false;
    private boolean victory = false;
    private UndoManager undoManager;

    public Field(int x, int y, int nMines)  {
        create(x, y, nMines);
        this.undoManager = new UndoManager();
    }

    public void create(int x, int y, int nMines)    {
        lines = x;
        columns = y;
        if (x < 1 || y < 1 || nMines < 0)   {
            return;
        }
        playingField = new ICell[x + 2][y + 2];
        for (int i = 0; i < playingField.length; i++)   {
            for (int j = 0; j < playingField[0].length; j ++)    {
                playingField[i][j] = new Cell(0);
            }
        }
        this.nMines = nMines;
        fill(x, y);
        notifyObservers();
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

    public ICell[][] getField() {
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
            LinkedList<ICell> revelalFieldCommandList = new LinkedList<ICell>();
            revealFieldHelp(x, y, revelalFieldCommandList);
            victory = checkVictory();
            undoManager.addEdit(new revealFieldCommand(revelalFieldCommandList));
        }
        notifyObservers();
    }
    
    private void revealFieldHelp(int x, int y, LinkedList<ICell> revelalFieldCommandList)  {
        playingField[x][y].setRevealed(true);
        revelalFieldCommandList.push(playingField[x][y]);
        if(playingField[x][y].getValue() > 0)  {
            return;
        } else {
            ArrayList<Point> fieldsaround = getFieldsAround(x, y);
            for(Point field : fieldsaround) {
                if((field.getX() > 0 && field.getY() > 0) && (field.getX() < playingField.length-1 && field.getY() < playingField[(int)field.getX()].length-1))    {
                    if(!playingField[(int)field.getX()][(int)field.getY()].isRevealed())    {
                        revealFieldHelp(field.x, field.y, revelalFieldCommandList);
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
    
    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
        notifyObservers();
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
        notifyObservers();
    }
    
}
