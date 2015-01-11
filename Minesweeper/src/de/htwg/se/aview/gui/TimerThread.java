package de.htwg.se.aview.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class TimerThread implements Runnable {

    private static Thread thread;
    private static Long time = new Long(Constances.ZERO);
    private static boolean stop = false;
    private static boolean started = false;

    public TimerThread() {
        thread = new Thread(this, "Timer");
    }

    @Override
    public void run() {
        time = new Long(Constances.ZERO);
        try {
            while (!stop) {
                BottomInfoPanel.setTimer(time++);
                Thread.sleep(Constances.SLEEP_TIME);
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

}
