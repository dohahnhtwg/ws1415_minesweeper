package de.htwg.se.aview.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.htwg.se.controller.IController;

public final class PlayingFieldPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static int buttonSize = 30;
    private JButton[][] buttons;
    private int i, j;
    private int x, y;
    private IController controller;
    public PlayingFieldPanel(final int x, final int y, IController controller) {
        this.controller = controller;
        buttons = new JButton[x][y];
        setLayout(new GridLayout(x, y));
        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addMouseListener(new MouseHandler(i, j, this, controller));
                buttons[i][j].setPreferredSize(new Dimension(buttonSize, buttonSize));;
                add(buttons[i][j]);
            }
    }
    
    public JButton[][] getButtons() {
        return buttons;
    }
    
    public void reEnableButtons() {
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++) {
                buttons[i][j].setEnabled(true);
            }
    }
    
    public void revealField(final int x, final int y) {
        
        buttons[x][y].setEnabled(false);
        buttons[x][y].setText(""); // getValue()
        repaint();
        revealField(x - 1, y);
        revealField(x - 1, y + 1);
        revealField(x, y + 1);
        revealField(x + 1, y + 1);
        revealField(x + 1, y);
        revealField(x + 1, y - 1);
        revealField(x, y - 1);
        revealField(x - 1, y - 1);
    }
}
