package de.htwg.se.aview.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class MouseHandler extends MouseAdapter {

    private int x, y;
    private PlayingFieldPanel field;

    private int index = Constances.ZERO;

    public MouseHandler(final int x, final int y, final PlayingFieldPanel panel)
    {
        this.x = x;
        this.y = y;
        field = panel;
    }

    public void mouseClicked(MouseEvent event) {

        if (event.getButton() == MouseEvent.BUTTON3 && field.getButtons()[x][y].isEnabled()) {
            index = ++index % Constances.buttonText.length;
            field.getButtons()[x][y].setText(Constances.buttonText[index]);
        }
    }

}
