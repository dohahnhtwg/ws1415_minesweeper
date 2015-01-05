package de.htwg.se.controller.impl;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;

public class ConcreteHandlerSize implements IHandler {

    private IHandler successor;

    @Override
    public void setSuccesor(IHandler successor) {
        this.successor = successor; 
    }

    @Override
    public boolean handleRequest(String request, IController controller) {
        if(request.equals("sS"))   {
            controller.create(9, 9, 10);
            return true;
        }
        if(request.equals("sM"))  {
            controller.create(16, 16, 40);
            return true;
        }
        if(request.equals("sL"))  {
            controller.create(16, 30, 99);
            return true;
        }
        return successor.handleRequest(request, controller);
    }

}
