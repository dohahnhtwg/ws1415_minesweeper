package de.htwg.se.controller.impl;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;

public class ConcreteHandlerNew implements IHandler {

    private IHandler successor;

    @Override
    public void setSuccesor(IHandler successor) {
        this.successor = successor;
    }
    
    @Override
    public boolean handleRequest(String request, IController controller) {
        if (request.equals("n"))  {
            controller.create();
            return true;
        }
        return successor.handleRequest(request, controller);
    }

}
