/* This file is part of Minesweeper.
 * 
 * Minesweeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minesweeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minesweeper.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.htwg.se.aview.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static de.htwg.se.aview.gui.Constants.ZERO;
import static de.htwg.se.aview.gui.Constants.ONE;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.htwg.se.controller.IController;
import de.htwg.se.model.ICell;

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
                buttons[i][j].setFocusPainted(true);
                buttons[i][j].setFont(new Font("Times New Roman", Font.BOLD, Constants.FONTSIZE));
                buttons[i][j].setMargin(new Insets(ZERO, ZERO, ZERO, ZERO));
                addListeners(controller, i, j);
                buttons[i][j].setPreferredSize(new Dimension(Constants.DEFBUTTONSIZE, Constants.DEFBUTTONSIZE));
                add(buttons[i][j]);
                reorgTextOnButton(controller, i, j);
            }
        }
    }

    private void addListeners(final IController controller, final int i, final int j) {
        buttons[i][j].addMouseListener(new MouseAdapter() {
            private int index = ZERO;
            @Override
            public void mouseClicked(final MouseEvent event) {

                if (event.getButton() == MouseEvent.BUTTON3 && buttons[i][j].isEnabled()) {
                    index = ++index % Constants.BUTTONTEXT.length;
                    buttons[i][j].setText(Constants.BUTTONTEXT[index]);
                    if (!marked.containsKey(i)) {
                        marked.put(i, new TreeMap<Integer, String>());
                    } 
                    marked.get(i).put(j, Constants.BUTTONTEXT[index]);
                }
            }
        });
        buttons[i][j].addActionListener(new ButtonHandler(i , j, controller));
    }

    private void reorgTextOnButton(final IController controller, final int i, final int j) {
        ICell cell = controller.getPlayingField().getField()[i + ONE][j + ONE];
        if (marked.containsKey(i) && marked.get(i).containsKey(j)) {
            buttons[i][j].setText(marked.get(i).get(j));
        }
        if (cell.isRevealed()) {
            buttons[i][j].setEnabled(false);
            if (cell.getValue() != 0) {
                buttons[i][j].setText(controller.getPlayingField().getField()[i + ONE][j + ONE].toString());
            }
        }
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public Map<Integer, Map<Integer, String>> getMarked() {
        return marked;
    }

    public static void zeroMarked() {
        TimerThread.resetTimer();
        marked = new HashMap<Integer, Map<Integer, String>>();
    }
    

}
