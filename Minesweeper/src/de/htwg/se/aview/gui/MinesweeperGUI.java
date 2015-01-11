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

    private int x = Constances.DEFDIMENSIONX;
    private int y = Constances.DEFDIMENSIONY;
	private IController controller;
    private JPanel mainPanel;
    private JPanel puppy = new JPanel();
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
        bip = new BottomInfoPanel(controller);
    }

    public final void constructMinesweeperGUI(final IController controller) {
    	x = controller.getPlayingField().getLines();
    	y = controller.getPlayingField().getColumns();
    	if (menu != null)
    		mainPanel.remove(menu);
    	menu = new MinesweeperMenuBar(controller);
    	mainPanel.add(menu, BorderLayout.NORTH);
    	if (puppy != null)
    		mainPanel.remove(puppy);
        mainPanel.add(puppy, BorderLayout.EAST);
        mainPanel.add(puppy, BorderLayout.WEST);
        if (field != null)
        	mainPanel.remove(field);
        field = new PlayingFieldPanel(x, y, controller);
        mainPanel.add(field, BorderLayout.CENTER);
        if (bip != null) {
        	mainPanel.remove(bip);
        } else
            bip = new BottomInfoPanel(controller);
        mainPanel.add(bip, BorderLayout.SOUTH);
        setResizable(false);
        setVisible(true);
        pack();
        repaint();
    }
    
    
    @Override
    public void update(Event e) {
    	constructMinesweeperGUI(controller);
        repaint();
        if (controller.isVictory())
            action("Congratulation! You win the game!");
        if (controller.isGameOver())
            action("GAME OVER!!!");
    }
    
    private void action(final String text) {
        TimerThread.stopTimer();
        JOptionPane.showMessageDialog(null, text + "\nTime: " + TimerThread.getTime() + " Seconds",
                "Spiel beendet", JOptionPane.INFORMATION_MESSAGE);
    }
}
