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

import de.htwg.se.controller.IController;

public final class ButtonHandler implements ActionListener {

    private static TimerThread timerThread;
    private int x, y;
    private IController controller;

    static {
        timerThread = new TimerThread();
    }
    public ButtonHandler(final int x, final int y, final IController controller) {
        this.x = x;
        this.y = y;
        this.controller = controller;
    }


    @Override
    public void actionPerformed(final ActionEvent arg0) {

        if (!timerThread.isStarted() && !controller.isGameOver() && !controller.isVictory()) {
            timerThread.startTimer();
        }
        controller.revealField(x + Constances.ONE, y + Constances.ONE);
    }

}
