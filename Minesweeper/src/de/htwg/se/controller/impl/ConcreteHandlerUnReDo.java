package de.htwg.se.controller.impl;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;

public class ConcreteHandlerUnReDo implements IHandler {

    private IHandler successor;
    
    @Override
    public void setSuccesor(IHandler successor) {
        this.successor = successor;
    }

    @Override
    public boolean handleRequest(String request, IController controller) {
        if(request.equals("u"))  {
            controller.undo();
            return true;
        }
        if(request.equals("r"))  {
            controller.redo();
            return true;
        }
        return successor.handleRequest(request, controller);
    }

}
