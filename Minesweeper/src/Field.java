/* A single field */
public class Field {

    int x;										//x Coordinate
    int y;										//y Coordinate
    private int minesCount = 0;					//number of mines around this field
	private boolean mine = false;  				//true if the field contains a mine, else false
	private boolean revealed = false;			//true if the field is revealed, else false
	
	public Field(int x, int y)	{
		this.x = x;
		this.y = y;
	}
	
	/* returns true if the Field contains a mine, else false */
	public boolean isMine() {
		return mine;
	}

	/* returns Number of Mines around the Field */
	public int getMinesCount() {
		return minesCount;
	}
	
	/* returns true if the Field is revealed else false */
	public boolean isRevealed() {
		return revealed;
	}
	
	
	
}
