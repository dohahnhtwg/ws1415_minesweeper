package de.htwg.se.aview.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public final class MouseHandler extends MouseAdapter {

    private int x, y; // instance variables
    private PlayingFieldPanel field;
    JButton[][] buttons;
    public MouseHandler(int x, int y, PlayingFieldPanel p)
    {
        this.x = x;
        this.y = y;
        field = p;
    }
    //Pruefen field[x][y]
    public void mouseClicked(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            field.getButtons()[x][y].setEnabled(false);
        }
        else if (event.getButton() == MouseEvent.BUTTON2) {
            field.getButtons()[x][y].setForeground(Color.RED);
        }
    }
}
