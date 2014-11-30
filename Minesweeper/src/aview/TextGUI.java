package aview;

import controller.Field;
import model.Cell;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/* Contains the TextGUI of Minesweeper */
public class TextGUI {

    private static final Logger LOGGER = Logger.getLogger("aviw.TextGUI");

    private TextGUI()   {
        PropertyConfigurator.configure("log4j.properties");
    }
    
    public static void main(String[] arg)   {
        TextGUI gui = new TextGUI();
        Field f = new Field(10, 10, 20);
        f.getField()[1][1].setRevealed(true);
        f.getField()[1][2].setRevealed(true);
        f.getField()[9][5].setRevealed(true);
        f.getField()[4][4].setRevealed(true);
        gui.paintField(f);
    }

    public void paintField(Field field) {
        StringBuilder sb = new StringBuilder("\n");
        int lines = field.getLines();
        int columns = field.getColumns();
        sb.append(String.format("%5s\n", "Line"));
        for (int i = 1; i <= lines; i++) {
            sb.append(String.format("%5d", i));
            for (int j = 1; j <= columns; j++)  {
                sb.append(paintCell(field.getField()[i][j]));
            }
            sb.append("\n");
        }
        sb.append("     ");
        for (int j = 1; j <= columns; j++)  {
            sb.append(String.format(" %2d ", j));
        }
        sb.append(String.format("\n       %s", "Column\n"));
        LOGGER.info(sb);
    }

    private static String paintCell(Cell cell)  {
        if(cell.isRevealed())   {
            if(cell.getValue() == -1)   {
                return String.format(" %2c ", '*');
            } else {
                return String.format(" %2s ", cell.getValue());
            }
        } else {
            return String.format(" %2c ", '-');
        }
    }
}
