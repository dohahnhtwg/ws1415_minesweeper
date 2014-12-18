package de.htwg.se.aview.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class BottomInfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField counter, timer;
    private JLabel counterLabel, timerLabel;
    public BottomInfoPanel() {
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
        Thread timerThread = new Thread(new Runnable() {
            Long time = new Long(0);
            @Override
            public void run() {
                while (true) { //to modify: as long as the field is not revealed
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
}
