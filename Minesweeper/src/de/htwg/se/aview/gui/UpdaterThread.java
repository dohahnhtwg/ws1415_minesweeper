package de.htwg.se.aview.gui;

import de.htwg.se.controller.IMainController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdaterThread implements Runnable {
    private IMainController controller;

    protected UpdaterThread(IMainController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            BottomInfoPanel.setTimer(controller.getCurrentTime());
            try {
                Thread.sleep(300);
            } catch (InterruptedException exc) {
                Logger.getLogger(BottomInfoPanel.class.getName()).log(Level.SEVERE, null, exc);
            }
        }
    }
}
