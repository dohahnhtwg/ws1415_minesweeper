package de.htwg.se.aview.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import de.htwg.se.controller.IController;

public final class MouseHandler extends MouseAdapter {

    private IController controller;
    private int x, y; // instance variables
    private PlayingFieldPanel field;
    private Object[] options = {"Exit", "New Game", "Cancel"};
    private int returnByLose = -1;
    private String[] buttonText = {"", "m", "?"};
    private int c = 0;
    public MouseHandler(int x, int y, PlayingFieldPanel p, IController controller)
    {
        this.controller = controller;
        this.x = x;
        this.y = y;
        field = p;
    }
    //Pruefen field[x][y]
    public void mouseClicked(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            field.getButtons()[x][y].setEnabled(false);
            field.getButtons()[x][y].setText("");
            controller.revealField(x, y);
            field.revealField(x, y);
            isGameOver();
            
        }
        else if (event.getButton() == MouseEvent.BUTTON3 && field.getButtons()[x][y].isEnabled()) {
            c = ++c % buttonText.length;
            field.getButtons()[x][y].setText(buttonText[c]);
        }
    }

    private void isGameOver() {
        if(controller.isGameOver()) {
            BottomInfoPanel.stopTimer();
            returnByLose = JOptionPane.showOptionDialog(null, "Leider verloren", "Spiel verloren",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
        }
        if (returnByLose == 0)
            System.exit(0);
        else if (returnByLose == 1) {
//            field.reEnableButtons();
            controller.create();
            
        }
    }
}
