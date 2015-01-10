package de.htwg.se.controller;

import de.htwg.se.model.IField;
import de.htwg.se.util.observer.IObservable;

public interface IController extends IObservable {

    /**
     * Creates a playing Field with the same parameters as old ones.
     */
    void create();
    
    /**
     * Creates a new playing Field.
     * @param lines number of lines.
     * @param columns number of columns.
     * @param nMines number of Mines.
     */
    void create(int lines, int columns, int nMines);
    
    /**
     * @return true if the Player is victorious.
     */
    boolean isVictory();
    
    /**
     * @return true if the Player lost the game.
     */
    boolean isGameOver();
    
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
    
    /**
     * @return the painting of a Field.
     */
    String getField();
    
    IField getPlayingField();
}
