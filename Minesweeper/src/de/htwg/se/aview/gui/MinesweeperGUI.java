package de.htwg.se.aview.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import de.htwg.se.controller.IController;
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;

public final class MinesweeperGUI extends JFrame implements IObserver {
    private static final long serialVersionUID = 1L;

    private final int x = 10;
    private final int y = 10;
    private IController controller;
    private JPanel mainPanel;
    private JMenuBar menu;
    private BottomInfoPanel bip;
    public MinesweeperGUI(IController controller) {
        this.controller = controller;
        controller.addObserver(this);
        new JFrame("Minesweeper");
        menu = new MinesweeperMenuBar(controller);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(menu, BorderLayout.NORTH);
        mainPanel.add(new PlayingFieldPanel(x, y, controller), BorderLayout.CENTER);
        mainPanel.add(new JPanel(), BorderLayout.EAST);
        mainPanel.add(new JPanel(), BorderLayout.WEST);
        bip = new BottomInfoPanel(controller);
        mainPanel.add(bip, BorderLayout.SOUTH);
        bip.runTimer();
        setLocationByPlatform(true);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        repaint();
    }

    @Override
    public void update(Event e) {
        controller.toString();
        repaint();
    }

}
