package de.htwg.se.aview.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.htwg.se.controller.IController;

public final class PlayingFieldPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton[][] buttons;
    private IController controller;

    public PlayingFieldPanel(final int x, final int y, final IController controller) {
        this.controller = controller;
        buttons = new JButton[x][y];
        setLayout(new GridLayout(x, y));
        String text = "";
        for (int i = Constances.ZERO; i < x; i++)
            for (int j = Constances.ZERO; j < y; j++) {
            	if (buttons[i][j] != null) text = buttons[i][j].getText();
                buttons[i][j] = new JButton(text);
                buttons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 20));
                buttons[i][j].setMargin(new Insets(Constances.ZERO, Constances.ZERO, Constances.ZERO, Constances.ZERO));
                buttons[i][j].addMouseListener(new MouseHandler(i, j, this));
                buttons[i][j].addActionListener(new ButtonHandler(i , j, this, this.controller));
                buttons[i][j].setPreferredSize(new Dimension(Constances.defButtonSize, Constances.defButtonSize));;
                add(buttons[i][j]);
                if (this.controller.getPlayingField().getField()[i + Constances.ONE][j + Constances.ONE].isRevealed()) {
                	buttons[i][j].setEnabled(false);
                	buttons[i][j].setText(this.controller.getPlayingField().getField()[i + Constances.ONE][j + Constances.ONE].toString());
                }
            }
    }
    
    public JButton[][] getButtons() {
        return buttons;
    }
    
}
