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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import de.htwg.se.controller.IController;

public class MinesweeperMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;

    private JMenu menu, menu2;
    private JMenuItem undo, redo, exit, newGame, statistik, info,  license;

    @SuppressWarnings("unused")
    private final IController controller;

    public MinesweeperMenuBar(final IController controller) {
        this.controller = controller;
        menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_DOWN_MASK));
        redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                InputEvent.CTRL_DOWN_MASK));
        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_DOWN_MASK));
        newGame = new JMenuItem("New Game");
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_DOWN_MASK));
        statistik = new JMenuItem("Statistic");
        statistik.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK));
        menu.add(newGame);
        menu.addSeparator();
        menu.add(undo);
        menu.add(redo);
        menu.add(statistik);
        menu.addSeparator();
        menu.add(exit);
        add(menu);

        menu2 = new JMenu("?");
        menu2.setMnemonic(KeyEvent.VK_I);
        info = new JMenuItem("About...");
        info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                InputEvent.CTRL_DOWN_MASK));
        license = new JMenuItem("License");
        license.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                InputEvent.CTRL_DOWN_MASK));
        menu2.add(info);
        menu2.add(license);
        add(menu2);

        undo.addActionListener(ActionListener -> controller.undo());

        redo.addActionListener(ActionListener -> controller.redo());

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(MinesweeperMenuBar.this,
                        "Exit Minesweeper?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (n == JOptionPane.YES_OPTION) {
                System.exit(0);
                }
            }
        });

        newGame.addActionListener(ActionListener -> new NewGameWindow(controller));

        statistik.addActionListener(ActionListener -> {
            final int loses = controller.getLoses();
            final int wins = controller.getVictories();
            double percentage = 0;
            if ((loses + wins) != 0) {
                percentage = wins * Constants.DEF_BUT_SIZEX/(loses + wins);
            }
            JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                    "Last time spend: " + TimerThread.getTime()
                    + "\nPlayed Games: " + (loses + wins)
                    + "\nWins: " + wins
                    + "\nPercentage: " + percentage + "%",
                    "Statistic", JOptionPane.INFORMATION_MESSAGE);
        });


        info.addActionListener(ActionListener -> JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                "<html><body>" + "<h3>Minesweeper</h3>"
                + "<p>This game was created to improve</p>"+"<p>understanding of the software engineering basics</p>"
                + "<p>like <i>life cycle of a software project</i>, <i>version control project</i>,</p>"
                + "<p><i>task management<i> etc.</p>"
                + "<p><b>by Dominik Hahn & Pavel Kravetskiy</b></p>" + "<hr />Software Engineering WS2014/2015"
                + "</body></html>", "Information",
                JOptionPane.INFORMATION_MESSAGE));
        
        license.addActionListener(ActionListener -> JOptionPane.showMessageDialog(MinesweeperMenuBar.this,
                "<html>" + "<body>" + "<h1><font color='green'>Minesweeper</font></h1>"
                + "Copyright (C) 2015 " +"<p>Dominik Hahn & Pavel Kravetskiy</p>"
                + "<a href='http://www.gnu.org/licenses/gpl.txt'>Under GNU GPL v3 license</a>"
                + "</body></html>", "License", JOptionPane.INFORMATION_MESSAGE));
    }

}
