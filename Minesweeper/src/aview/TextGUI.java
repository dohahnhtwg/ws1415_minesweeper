package aview;

import controller.Field;
import model.Cell;

/* Contains the TextGUI of Minesweeper */
public class TextGUI {
	
	
	public static void main(String[] arg)	{
		Field f = new Field(10, 10, 20);
		f.getField()[1][1].setRevealed(true);
		f.getField()[1][2].setRevealed(true);
		f.getField()[9][5].setRevealed(true);
		f.getField()[4][4].setRevealed(true);
		paintField(f);
	}
	
	
	public TextGUI()	{
		
	}
	
	public static void paintField(Field field)	{
		int lines = field.getLines();
		int columns = field.getColumns();
		System.out.printf("%5s\n", "Line");
		for (int i = 1; i <= lines; i++) {
			System.out.printf("%5d", i);
			for (int j = 1; j <= columns; j++)
				paintCell(field.getField()[i][j]);
			System.out.println();
		}
		System.out.print("     ");
		for (int j = 1; j <= columns; j++)
			System.out.printf(" %2d ", j);
		System.out.printf("\n       %s", "Column");
	}
	
	private static void paintCell(Cell cell)	{
		if(cell.isRevealed())	{
			if(cell.getValue() == -1)	{
				System.out.printf(" %2s ", "*");
			} else {
				System.out.printf(" %2s ", cell.getValue());
			}
		} else {
			System.out.printf(" %2s ", "-");
		}
	}

}
