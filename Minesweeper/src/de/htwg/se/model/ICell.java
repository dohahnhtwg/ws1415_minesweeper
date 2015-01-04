package de.htwg.se.model;

public interface ICell {

    /**
     * @return the Value of the Cell. -1 stands for a Mine and 0-6 for the 
     *         number of Mines around.
     */
    int getValue();
    
    /** 
     * @param newValue is the new Value. -1 stands for a Mine and 0-6 for the 
     *        number of Mines around.
     */
    void setValue(int newValue);
    
    /**
     * @return true if the Cell is Revealed.
     */
    boolean isRevealed();
    
    /**
     * @param isRevealed
     */
    void setRevealed(boolean isRevealed);
    
    /**
     * @return String representing a Cell.
     */
    String toString();
}
