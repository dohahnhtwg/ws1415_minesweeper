/* A single field */
public class Field {

    int x;										//x Coordinate
    int y;										//y Coordinate
    private int minesAround = 0;				//number of mines around this field
	private boolean mine = false;  				//true if the field contains a mine, else false
	private boolean revealed = false;			//true if the field is revealed, else false
	
	public Field(int x, int y)	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * returns number of Mines around the field
	 * @return number of Mines around the field
	 */
	public int isMine() {
		return minesAround;
	}

	/**
	 * returns true if the Field contains a mine, else false
	 * @return true if the field contains a mine
	 */
	public boolean getMine() {
		return mine;
	}
	
	/**
	 *  returns true if the Field is revealed else false 
	 *  @return true if field is revealed
	 */  
	public boolean getRevealed() {
		return revealed;
	}
	
	
	
}
