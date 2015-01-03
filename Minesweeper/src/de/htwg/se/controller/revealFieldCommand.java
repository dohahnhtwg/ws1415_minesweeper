package de.htwg.se.controller;

import java.util.LinkedList;

import javax.swing.undo.AbstractUndoableEdit;

import de.htwg.se.model.ICell;

public class revealFieldCommand extends AbstractUndoableEdit    {

    private LinkedList<ICell> cellList;
    private LinkedList<Integer> undobuffer;
    private static final long serialVersionUID = 1L;

    public revealFieldCommand(LinkedList<ICell> cellList)    {
        this.cellList = cellList;
        undobuffer = new LinkedList<Integer>();
    }
    
    public void undo()  {
        for(ICell x : cellList)  {
            undobuffer.push(x.getValue());
            x.setValue(0);
            x.setRevealed(false);
        }
    }
    
    public void redo()  {
        for(ICell x : cellList)  {
            x.setValue(undobuffer.pollLast());
            x.setRevealed(true);
        }
    }
    
    public boolean canUndo() {
        return true;
    }

    public boolean canRedo() {
        return true;
    }
}
