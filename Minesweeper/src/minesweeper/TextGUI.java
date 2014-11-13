package minesweeper;

/* Contains the TextGUI of Minesweeper */
public class TextGUI {
	
	/**
	 * main
	 */
	public static void main(String[] arg)	{
		Field f = new Field(10, 10, 20);
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++)
				System.out.printf(" %2d ", f.getField()[i][j].getValue()==0 ? 0 : f.getField()[i][j].getValue());
			System.out.println();
		}
	}
	
	
	public TextGUI()	{
		
	}
	
	/**
	 * paints the playing field in the console
	 * @param playing_field is the actual playing field
	 */
	public void paintField(Field playing_field)	{
		
	}

}
