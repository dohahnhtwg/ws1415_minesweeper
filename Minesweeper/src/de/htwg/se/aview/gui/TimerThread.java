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

import java.util.logging.Level;
import java.util.logging.Logger;

public final class TimerThread implements Runnable {

    private static Thread thread;
    private static Long time = new Long(Constants.ZERO);
    private static boolean stop = false;
    private static boolean started = false;

    public TimerThread() {
        thread = new Thread(this, "Timer");
    }

    @Override
    public void run() {
        time = new Long(Constants.ZERO);
        try {
            while (!stop) {
                BottomInfoPanel.setTimer(time++);
                Thread.sleep(Constants.SLEEP_TIME);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BottomInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void stopTimer() {
        stop = true;
        started = false;
    }

    public static Long getTime() {
        return time;
    }

    public void startTimer() {

        stop = false;
        started = true;

        try {
            this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BottomInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        thread.start();
    }

    public boolean isStarted() {
        return started;
    }
    
    public static void resetTimer() {
        time = new Long(0);
    }
}
