package de.htwg.se.aview.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.se.controller.IController;

public final class BottomInfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static JTextField timer;
    static {
        timer = new JTextField("0", Constances.THREE);
    }
    public BottomInfoPanel(IController controller) {
        JTextField counter;
        JLabel counterLabel, timerLabel;
        new JPanel();
        counterLabel = new JLabel("Mines");
        timerLabel = new JLabel("Seconds");
        Integer nMines = controller.getPlayingField().getnMines();
        counter = new JTextField(nMines.toString(), Constances.THREE);
        timer = new JTextField(TimerThread.getTime().toString(), Constances.THREE);
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

    public static void setTimer(Long time) {
        timer.setText(time.toString());
    }
}
