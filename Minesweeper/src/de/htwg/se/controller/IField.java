package de.htwg.se.controller;

import de.htwg.se.model.ICell;
import de.htwg.se.util.observer.IObservable;

public interface IField extends IObservable {

    /**
     * Creates a new playing Field
     * @param x is the number of lines.
     * @param y is the number of columns.
     * @param nMines is the number of Mines in the playing Field.
     */
    void create(int x, int y, int nMines);
    
    /**
     * @return number of lines.
     */
    int getLines();
    
    /**
     * @return number of columns.
     */
    int getColumns();
    /**
     * @return number of Mines.
     */
    int getnMines();
    
    /**
     * @return true if the Player is victorious.
     */
    boolean isVictory();
    
    /**
     * @return true if the Player lost the game.
     */
    boolean isGameOver();
    
    /**
     * @return whole playing Field.
     */
    ICell[][] getField();
    
    /**
     * reveal the Field with the given Coordinates.
     * @param x is the x-Coordinate.
     * @param y is the y-Coordinate.
     */
    void revealField(int x, int y);
    
    /**
     * undo the last revealeFieldCommand.
     */
    void undo();
    
    /**
     * redo the last undo.
     */
    void redo();
}
