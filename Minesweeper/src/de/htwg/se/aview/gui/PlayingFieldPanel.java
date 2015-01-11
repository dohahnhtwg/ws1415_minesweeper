package de.htwg.se.aview.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import static de.htwg.se.aview.gui.Constances.ZERO;
import static de.htwg.se.aview.gui.Constances.ONE;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.htwg.se.controller.IController;

public final class PlayingFieldPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton[][] buttons;
    private static Map<Integer, Map<Integer, String>> marked = new TreeMap<>();

    static {
        marked = new HashMap<Integer, Map<Integer, String>>();
    }
    public PlayingFieldPanel(final int x, final int y, final IController controller) {
        buttons = new JButton[x][y];
        setLayout(new GridLayout(x, y));

        for (int i = ZERO; i < x; i++) {
            for (int j = ZERO; j < y; j++) {

                buttons[i][j] = new JButton();
                if (marked.containsKey(i) && marked.get(i).containsKey(j)) {
                    buttons[i][j].setText(marked.get(i).remove(j));
                }
                buttons[i][j].setFocusPainted(true);
                buttons[i][j].setFont(new Font("Times New Roman", Font.BOLD, Constances.FONTSIZE));
                buttons[i][j].setMargin(new Insets(ZERO, ZERO, ZERO, ZERO));
                buttons[i][j].addMouseListener(new MouseHandler(i, j, this));
                buttons[i][j].addActionListener(new ButtonHandler(i , j, this, controller));
                buttons[i][j].setPreferredSize(new Dimension(Constances.DEFBUTTONSIZE, Constances.DEFBUTTONSIZE));
                add(buttons[i][j]);
                if (controller.getPlayingField().getField()[i + ONE][j + ONE].isRevealed()) {
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setText(controller.getPlayingField().getField()[i + ONE][j + ONE].toString());
                }
            }
        }
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public Map<Integer, Map<Integer, String>> getMarked() {
        return marked;
    }
}
