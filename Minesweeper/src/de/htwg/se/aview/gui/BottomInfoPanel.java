package de.htwg.se.aview.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.se.controller.IController;

public final class BottomInfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField counter, timer;
    private JLabel counterLabel, timerLabel;
    private static Long time;
    private static boolean stop = false;
    private IController controller;
    private static Thread timerThread;
    public BottomInfoPanel(IController controller) {
        this.controller = controller;
        new JPanel();
        counterLabel = new JLabel("Mines");
        timerLabel = new JLabel("Seconds");
        counter = new JTextField("0", 3);
        timer = new JTextField("0", 3);
        counter.setBackground(Color.black);
        counter.setDisabledTextColor(Color.YELLOW);
        counter.setEnabled(false);
        counter.setForeground(Color.BLACK);

        timer.setBackground(Color.black);
        timer.setDisabledTextColor(Color.YELLOW);
        timer.setEnabled(false);
        timer.setForeground(Color.BLACK);
        
        add(counterLabel);
        add(counter);
        add(timerLabel);
        add(timer);
    }
    
    public void runTimer() {
        timerThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                time = new Long(0);
                while (time < 1000 || !controller.isGameOver() || !controller.isVictory() || stop) {
                    timer.setText((time++).toString());
                    try {
                        Thread.sleep(990);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timerThread.start();
    }
    
    public static void stopTimer() {
        if (timerThread.isAlive())
            stop = true;
    }
    
    public static long getTime() {
        return time;
    }
}
