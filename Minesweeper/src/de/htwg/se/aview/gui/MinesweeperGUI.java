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

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.inject.Inject;

import de.htwg.se.controller.IController;
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;

public final class MinesweeperGUI extends JFrame implements IObserver {
    private static final long serialVersionUID = 1L;

    private IController controller;
    private JPanel mainPanel;
    private JPanel sidePanel1, sidePanel2;
    private JMenuBar menu;
    private BottomInfoPanel bip;
    private PlayingFieldPanel field;

    @Inject
    public MinesweeperGUI(final IController controller) {
        this.controller = controller;
        controller.addObserver(this);
        new JFrame("Minesweeper");
        mainPanel = new JPanel(new BorderLayout());
        setLocationByPlatform(true);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        constructMinesweeperGUI(controller);
    }

    public void constructMinesweeperGUI(final IController controller) {
        int x = controller.getPlayingField().getLines();
        int y = controller.getPlayingField().getColumns();
        if (menu != null) {
            mainPanel.remove(menu);
        }
        menu = new MinesweeperMenuBar(controller);
        mainPanel.add(menu, BorderLayout.NORTH);
        if (sidePanel1 != null) {
            mainPanel.remove(sidePanel1);
        }
        sidePanel1 = new JPanel();
        mainPanel.add(sidePanel1, BorderLayout.EAST);
        if (sidePanel2 != null) {
            mainPanel.remove(sidePanel2);
        }
        sidePanel2 = new JPanel();
        mainPanel.add(sidePanel2, BorderLayout.WEST);
        if (field != null) {
            mainPanel.remove(field);
        }
        field = new PlayingFieldPanel(x, y, controller);
        mainPanel.add(field, BorderLayout.CENTER);
        if (bip != null) {
            mainPanel.remove(bip);
        }
        bip = new BottomInfoPanel(controller);
        mainPanel.add(bip, BorderLayout.SOUTH);
        setResizable(false);
        setVisible(true); 
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, e);
        }
        pack();
        repaint();
    }


    @Override
    public void update(final Event e) {
        constructMinesweeperGUI(controller);
        repaint();
        if (controller.isVictory()) {
            action("Congratulation! You win the game!");
            PlayingFieldPanel.zeroMarked();
        }
        if (controller.isGameOver()) {
            action("GAME OVER!!!");
            PlayingFieldPanel.zeroMarked();
        }
    }

    private void action(final String text) {
        TimerThread.stopTimer();
        JOptionPane.showMessageDialog(null, text + "\nTime: " + TimerThread.getTime() + " Seconds",
                "Game ended", JOptionPane.INFORMATION_MESSAGE);
    }
}
