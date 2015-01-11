package de.htwg.se.aview.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class TimerThread implements Runnable {

    private static Thread thread;
    private static Long time = new Long(0);
    private static boolean stop = false;
    private static boolean started = false;
    
    public TimerThread() {
        thread = new Thread(this, "Timer");
    }

    @Override
    public void run() {
        time = new Long(0);
        try {
            while (!stop) {
                BottomInfoPanel.setTimer(time++);
                Thread.sleep(999);
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
