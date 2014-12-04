package aview;

import java.util.Observable;
import java.util.Observer;

import controller.Field;
import model.Cell;

import org.apache.log4j.Logger;

/* Contains the TextGUI of Minesweeper */
public class TextGUI implements Observer {

    private Field field;
    private static final Logger LOGGER = Logger.getLogger("aview.TextGUI");

    public TextGUI(Field field)   {
        this.field = field;
        field.addObserver(this);
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

    public boolean processInputLine(String next) {
        boolean proceed = true;
        switch(next)    {
        case "q":
            proceed = false;
            break;
        }
        if (next.matches("[0-9][0-9]-[0-9][0-9]")) {
            String[] parts = next.split("-");
            field.revealField(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } else {
            LOGGER.info("illegal argument");
        }
        return proceed;
    }

    @Override
    public void update(Observable o, Object arg) {
           paintField(field);
    }
}
