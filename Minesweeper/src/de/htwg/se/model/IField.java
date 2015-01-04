package de.htwg.se.model;

public interface IField {

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
     * @return whole playing Field.
     */
    ICell[][] getField();
    
    /**
     * @return String representing a Field.
     */
    String toString();
    
    
}
