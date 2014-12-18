package de.htwg.se.aview.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public final class MinesweeperGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    private JMenuBar menu;
    private BottomInfoPanel bip;
    public MinesweeperGUI(final int x, final int y) {
        new JFrame("Minesweeper");
        menu = new MinesweeperMenuBar();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(menu, BorderLayout.NORTH);
        mainPanel.add(new PlayingField(x, y), BorderLayout.CENTER);
        mainPanel.add(new JPanel(), BorderLayout.EAST);
        mainPanel.add(new JPanel(), BorderLayout.WEST);
        bip = new BottomInfoPanel();
        mainPanel.add(bip, BorderLayout.SOUTH);
        bip.runTimer();
        setLocationByPlatform(true);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }
    
    
    
    public static void main(String[] args) {
        new MinesweeperGUI(10, 20);

    }

}
