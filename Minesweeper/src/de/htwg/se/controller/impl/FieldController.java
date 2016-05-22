package de.htwg.se.controller.impl;

import akka.actor.UntypedActor;
import de.htwg.se.aview.tui.messages.UpdateMessage;
import de.htwg.se.controller.RevealFieldCommand;
import de.htwg.se.controller.messages.RevealFieldMessage;
import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;
import de.htwg.se.model.impl.Cell;
import de.htwg.se.model.impl.Field;

import javax.swing.undo.UndoManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by GAAB on 22.05.2016.
 */
public class FieldController {

    private IField field;
    private UndoManager undoManager;
    private static final int DEFDIMENS = 9;
    private static final int DEFNMINES = 10;
    private static final int BORDER = 2;
    private static final int THREE_CELL_LEN = 3;

    public FieldController()    {
        field = new Field();
        create(DEFDIMENS, DEFDIMENS, DEFNMINES);
        undoManager = new UndoManager();
    }

    public IField getField()    {
        return field;
    }

    public void setField(IField field)   {
        this.field = field;
    }

    public void restart()   {
        create(field.getLines(), field.getColumns(), field.getnMines());
    }

    public void create(int lines, int columns, int nMines)    {
        field.setIsGameOver(false);
        field.setIsVictory(false);
        field.setLines(lines);
        field.setColumns(columns);
        field.setnMines(nMines);
        if (lines < 1 || columns < 1 || nMines < 0)   {
            throw new IllegalArgumentException("lines, columns or nMines too small.");
        }
        ICell[][] field = new ICell[lines + BORDER][columns + BORDER];
        for (int i = 0; i < field.length; i++)   {
            for (int j = 0; j < field[0].length; j ++)    {
                field[i][j] = new Cell(0);
            }
        }
        fill(field, lines, columns, nMines);
        this.field.setPlayingField(field);
    }

    public void revealField(RevealFieldMessage msg) {
        if(field.getField()[msg.getX()][msg.getY()].getValue() == -1) {
            field.getField()[msg.getX()][msg.getY()].setIsRevealed(true);
            field.setIsGameOver(true);
        } else {
            List<ICell> revealFieldCommandList = new LinkedList<>();
            revealFieldHelp(msg.getX(), msg.getY(), revealFieldCommandList);
            field.setIsVictory(checkVictory());
            undoManager.addEdit(new RevealFieldCommand(revealFieldCommandList));
        }
    }

    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    private void fill(ICell[][] field, int lines, int columns, int nMines) {
        Long timeForHash = System.nanoTime();
        int hash = timeForHash.hashCode();
        Random rnd = new Random(hash);
        for (int i = 0; i < nMines; i++)    {
            generateMines(field, lines, columns, rnd);
        }
        generateMinesAround(field);
    }

    private void generateMines(ICell[][] field, int lines, int columns, Random rnd)    {
        int x = rnd.nextInt(lines /*- 1*/) + 1;
        int y = rnd.nextInt(columns /*- 1*/) + 1;
        if (field[x][y].getValue() != -1)    {
            field[x][y].setValue(-1);
        } else  {
            generateMines(field, lines, columns, rnd);
        }
    }

    private void generateMinesAround(ICell[][] field) {
        for (int i = 1; i < field.length - 1; i++)   {
            for (int j = 1; j < field[0].length - 1; j ++)   {
                if (field[i][j].getValue() != -1)    {
                    field[i][j].setValue(nMinesAroundAPoint(field, i, j));
                }
            }
        }
    }

    private int nMinesAroundAPoint(ICell[][] field, int x, int y)    {
        int mines = 0;
        for (int i = 0; i < THREE_CELL_LEN; i++) {
            for (int j = 0; j < THREE_CELL_LEN; j++) {
                if (field[x - 1 + i][y - 1 + j].getValue() == -1)    {
                    mines++;
                }
            }
        }
        return mines;
    }

    private void revealFieldHelp(int x, int y, List<ICell> revelalFieldCommandList)  {
        field.getField()[x][y].setIsRevealed(true);
        ((LinkedList<ICell>) revelalFieldCommandList).push(field.getField()[x][y]);
        if(field.getField()[x][y].getValue() <= 0)  {
            List<Point> fieldsAround = getFieldsAround(x, y);
            for(Point field : fieldsAround) {
                if(checkCellInField(field) && !this.field.getField()[field.x][field.y].getIsRevealed()) {
                    revealFieldHelp(field.x, field.y, revelalFieldCommandList);
                }
            }
        }
    }

    private List<Point> getFieldsAround(int x, int y) {
        List<Point> fieldsAround = new ArrayList<>();
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

    private boolean checkCellInField(Point cell)    {
        return (cell.getX() > 0 && cell.getY() > 0)
                && (cell.getX() < field.getField().length - 1
                && cell.getY() < field.getField()[(int) cell.getX()].length - 1);
    }

    private boolean checkVictory()  {
        int requirement = field.getLines() * field.getColumns() - field.getnMines();
        int current = 0;

        for (int i = 0; i < field.getField().length; i++)   {
            for (int j = 0; j < field.getField()[0].length; j ++)    {
                if(field.getField()[i][j].getIsRevealed()) {
                    current++;
                }
            }
        }

        return current == requirement;
    }
}
