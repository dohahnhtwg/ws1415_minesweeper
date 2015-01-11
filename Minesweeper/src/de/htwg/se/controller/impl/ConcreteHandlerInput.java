package de.htwg.se.controller.impl;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;

public class ConcreteHandlerInput implements IHandler {

    private IHandler successor;

    @Override
    public void setSuccesor(IHandler successor) {
        this.successor = successor; 
    }

    @Override
    public boolean handleRequest(String request, IController controller) {
        if (request.matches("[0-9][0-9]-[0-9][0-9]")) {
            String[] parts = request.split("-");
            controller.revealField(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            return true;
        }
        return false;
    }

    @Override
    public IHandler getSuccesor() {
        return successor;
    }

}
