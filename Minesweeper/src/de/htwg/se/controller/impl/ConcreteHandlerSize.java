package de.htwg.se.controller.impl;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;

public class ConcreteHandlerSize implements IHandler {

    private IHandler successor;
    private int smallDimens = 9;
    private int medDimens = 16;
    private int largeDimens = 30;
    private int smallMines = 10;
    private int medMines = 40;
    private int largeMines = 99;

    @Override
    public void setSuccesor(IHandler successor) {
        this.successor = successor; 
    }

    @Override
    public boolean handleRequest(String request, IController controller) {
        if(request.equals("sS"))   {
            controller.create(smallDimens, smallDimens, smallMines);
            return true;
        }
        if(request.equals("sM"))  {
            controller.create(medDimens, medDimens, medMines);
            return true;
        }
        if(request.equals("sL"))  {
            controller.create(medDimens, largeDimens, largeMines);
            return true;
        }
        return successor.handleRequest(request, controller);
    }

    @Override
    public IHandler getSuccesor() {
        return successor;
    }

}
