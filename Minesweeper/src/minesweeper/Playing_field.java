package minesweeper;


/* The whole playing field */
public class Playing_field {
	
	private Field [][] fields;
	private int numberOfMines;
	private int lines;
	private int columns;
	

	public Playing_field(int length, int hight)	{
		fields = new Field[length][hight];
		createPlayingField(length, hight);
	}
	
	/**
	 * creates a new playing Field
	 * @param lines of the field
	 * @param columns of the field
	 */
	public void createPlayingField(int lines, int columns)	{
		
	}
	
	/**
	 * checks the number of minesAround a field
	 * @param field 
	 * @return
	 */
	public int checkMinesAround(Field field)	{
		return 0;
	}
	
	/**
	 * reveal a field and modifies the playing field
	 * @param field which gets revealed
	 */
	public void revealField(Field field)	{
		
	}
	
	/**
	 * addMines on the field
	 * @param numberofMines how many mines
	 */
	public void addMines(int numberofMines)	{
		
	}
	
	/**
	 * returns a field
	 * @return the actual field
	 */
	public Field[][] getFields() {
		return fields;
	}
	
	
}
