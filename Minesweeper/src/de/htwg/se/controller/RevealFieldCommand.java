package de.htwg.se.controller;

import java.util.LinkedList;
import java.util.List;

import javax.swing.undo.AbstractUndoableEdit;

import de.htwg.se.model.ICell;

public class RevealFieldCommand extends AbstractUndoableEdit    {

    private List<ICell> cellList;
    private List<Integer> undobuffer;
    private static final long serialVersionUID = 1L;

    public RevealFieldCommand(List<ICell> cellList)    {
        this.cellList = cellList;
        undobuffer = new LinkedList<Integer>();
    }
    
    public void undo()  {
        for(ICell x : cellList)  {
            ((LinkedList<Integer>) undobuffer).push(x.getValue());
            x.setValue(0);
            x.setRevealed(false);
        }
    }
    
    public void redo()  {
        for(ICell x : cellList)  {
            x.setValue(((LinkedList<Integer>) undobuffer).pollLast());
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
