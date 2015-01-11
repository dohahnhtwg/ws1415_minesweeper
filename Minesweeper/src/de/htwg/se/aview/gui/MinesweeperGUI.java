package de.htwg.se.aview.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
        pack();
        repaint();
    }


    @Override
    public void update(final Event e) {
        constructMinesweeperGUI(controller);
        repaint();
        if (controller.isVictory()) {
            action("Congratulation! You win the game!");
        }
        if (controller.isGameOver()) {
            action("GAME OVER!!!");
        }
    }

    private void action(final String text) {
        TimerThread.stopTimer();
        JOptionPane.showMessageDialog(null, text + "\nTime: " + TimerThread.getTime() + " Seconds",
                "Spiel beendet", JOptionPane.INFORMATION_MESSAGE);
    }
}
