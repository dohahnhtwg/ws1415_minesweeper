package de.htwg.se.aview.gui;

import akka.actor.ActorRef;
import de.htwg.se.controller.messages.MainController.TimeRequest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdaterThread implements Runnable {
    private ActorRef controller;
    private ActorRef parentRef;

    protected UpdaterThread(ActorRef controller, ActorRef parentRef) {
        this.parentRef = parentRef;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            controller.tell(new TimeRequest(), parentRef);
            try {
                Thread.sleep(300);
            } catch (InterruptedException exc) {
                Logger.getLogger(BottomInfoPanel.class.getName()).log(Level.SEVERE, null, exc);
            }
        }
    }
}
