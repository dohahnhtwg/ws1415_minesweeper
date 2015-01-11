package de.htwg.se.aview.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeMap;

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

    @Override
    public void mouseClicked(final MouseEvent event) {

        if (event.getButton() == MouseEvent.BUTTON3 && field.getButtons()[x][y].isEnabled()) {
            index = ++index % Constances.BUTTONTEXT.length;
            field.getButtons()[x][y].setText(Constances.BUTTONTEXT[index]);
            field.getMarked().put(x, new TreeMap<Integer, String>());
            field.getMarked().get(x).put(y, Constances.BUTTONTEXT[index]);
        }
    }

}
