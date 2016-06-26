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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static de.htwg.se.aview.gui.Constants.ZERO;
import static de.htwg.se.aview.gui.Constants.ONE;

import javax.swing.*;

import akka.actor.ActorRef;
import de.htwg.se.controller.messages.RevealCellRequest;
import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;

public final class PlayingFieldPanel extends JPanel {
    private ActorRef controller;
    private ActorRef parentRef;
    private IField playingField;
    private static final long serialVersionUID = 1L;
    private JButton[][] buttons;
    private static Map<Integer, Map<Integer, ImageIcon>> marked;

    static {
        marked = new HashMap<>();
    }
    public PlayingFieldPanel(final IField playingField, final ActorRef controller, final ActorRef parentRef) {
        super();
        this.parentRef = parentRef;
        this.controller = controller;
        this.playingField = playingField;
        createButtons();
    }

    private void addListeners(final int i, final int j) {
        buttons[i][j].addMouseListener(new MouseAdapter() {
            private int index = ZERO;
            @Override
            public void mousePressed(final MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON3 && buttons[i][j].isEnabled()) {
                    buttons[i][j].setIcon(Constants.getIcon(index));
                    if (!marked.containsKey(i)) {
                        marked.put(i, new TreeMap<Integer, ImageIcon>());
                    } 
                    marked.get(i).put(j, Constants.getIcon(index++));
                }
            }
        });
        buttons[i][j].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttons[i][j].getIcon() != null) {
                    return;
                }
                controller.tell(new RevealCellRequest(i + ONE, j + ONE), parentRef);
            }
        });
    }

    private void updateButton(final int i, final int j) {
        ICell cell = playingField.getPlayingField()[i + ONE][j + ONE];
        if (marked.containsKey(i) && marked.get(i).containsKey(j)) {
            buttons[i][j].setIcon(marked.get(i).get(j));
        }
        if (cell.isRevealed()) {
            buttons[i][j].setEnabled(false);
            buttons[i][j].setIcon(null);
            if (cell.getValue() != 0) {
                String value = playingField.getPlayingField()[i + ONE][j + ONE].toString();
                if (value.equals("  * ")) {
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setIcon(Constants.getMineIcon());
                } else {
                    buttons[i][j].setText(value);
                }
            }
        }
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public Map<Integer, Map<Integer, ImageIcon>> getMarked() {
        return marked;
    }

    public static void zeroMarked() {
        marked = new HashMap<>();
    }

    public void updateField(IField playingField) {
        int x = playingField.getLines();
        int y = playingField.getColumns();
        for (int i = ZERO; i < x+2; i++) {
            for (int j = ZERO; j < y+2; j++) {
                updateButton(i, j);
                if (buttons[i][j].isEnabled()) {
                    addListeners(i, j);
                }
            }
        }
    }

    private void createButtons() {
        int x = playingField.getLines();
        int y = playingField.getColumns();
        buttons = new JButton[x][y];
        setLayout(new GridLayout(x, y));

        for (int i = ZERO; i < x; i++) {
            for (int j = ZERO; j < y; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFocusPainted(true);
                buttons[i][j].setFont(new Font("Times New Roman", Font.BOLD, Constants.FONTSIZE));
                buttons[i][j].setMargin(new Insets(ZERO, ZERO, ZERO, ZERO));
                updateButton(i, j);
                if (buttons[i][j].isEnabled()) {
                    addListeners(i, j);
                }
                buttons[i][j].setPreferredSize(new Dimension(Constants.DEFBUTTONSIZE, Constants.DEFBUTTONSIZE));
                add(buttons[i][j]);
            }
        }
    }
}
