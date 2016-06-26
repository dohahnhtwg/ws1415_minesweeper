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

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class BottomInfoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static JTextField timer;
    private JTextField counter;
    static {
        timer = new JTextField("", Constants.THREE);
    }
    public BottomInfoPanel() {
        super();
        JLabel counterLabel, timerLabel;
        new JPanel();
        counterLabel = new JLabel("Mines");
        timerLabel = new JLabel("Seconds");
        counter = new JTextField("", Constants.THREE);
        timer = new JTextField("", Constants.THREE);
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

    public void setTimer(final Long time) {
        timer.setText(time.toString());
    }

    public void setCounterText(String value) {
        counter.setText(value);
    }
}
