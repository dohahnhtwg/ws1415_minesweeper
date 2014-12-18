package de.htwg.se.aview.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public final class PlayingField extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton[][] buttons;
    private int i, j;
    public PlayingField(final int x, final int y) {
        buttons = new JButton[x][y];
        setLayout(new GridLayout(x, y));
        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++) {
                buttons[i][j] = new JButton();
//                buttons[i][j].addMouseListener(new MouseHandler(i, j, this));
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    
                    @Override
                    public void mouseClicked(MouseEvent event) {
                        if (event.equals(MouseEvent.BUTTON1)) {
                            buttons[i][j].setEnabled(false);
                        }
                        else if (event.equals(MouseEvent.BUTTON2)) {
                            buttons[i][j].setForeground(Color.RED);
                        }
                    }
                });
                buttons[i][j].setPreferredSize(new Dimension(30, 30));;
                add(buttons[i][j]);
            }
    }
    
    public JButton[][] getButtons() {
        return buttons;
    }
}
