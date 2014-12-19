package de.htwg.se.aview.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public final class PlayingFieldPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static int buttonSize = 30;
    private JButton[][] buttons;
    private int i, j;
    public PlayingFieldPanel(final int x, final int y) {
        buttons = new JButton[x][y];
        setLayout(new GridLayout(x, y));
        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addMouseListener(new MouseHandler(i, j, this));
                buttons[i][j].setPreferredSize(new Dimension(buttonSize, buttonSize));;
                add(buttons[i][j]);
            }
    }
    
    public JButton[][] getButtons() {
        return buttons;
    }
}
