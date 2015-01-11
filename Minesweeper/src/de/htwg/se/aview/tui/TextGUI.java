package de.htwg.se.aview.tui;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import de.htwg.se.controller.IHandler;
import de.htwg.se.controller.IController;
import de.htwg.se.controller.impl.ConcreteHandlerInput;
import de.htwg.se.controller.impl.ConcreteHandlerNew;
import de.htwg.se.controller.impl.ConcreteHandlerSize;
import de.htwg.se.controller.impl.ConcreteHandlerUnReDo;
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;

public class TextGUI implements IObserver {

    private IController controller;
    private static final Logger LOGGER = Logger.getLogger("aview.TextGUI");
    private IHandler handlerNew;
    private IHandler handlerSize;
    private IHandler handlerUnReDo;
    private IHandler handlerInput;

    @Inject
    public TextGUI(IController controller)   {
        this.controller = controller;
        controller.addObserver(this);
        
        handlerNew = new ConcreteHandlerNew();
        handlerSize = new ConcreteHandlerSize();
        handlerUnReDo = new ConcreteHandlerUnReDo();
        handlerInput = new ConcreteHandlerInput();
        handlerNew.setSuccesor(handlerSize);
        handlerSize.setSuccesor(handlerUnReDo);
        handlerUnReDo.setSuccesor(handlerInput);
        handlerInput.setSuccesor(null);
        
    }

    public void paintTUI() {
        LOGGER.info(controller.getField());
        LOGGER.info("Possible commands: q = quit, n = new Game, sS = small Field, sM = medium Field, sL = Large Field, yy-xx = reveal Field, u = undo, r = redo");
    }

    public boolean processInputLine(String next) {
        boolean proceed = true;
        
        if(next.equals("q")) {
            proceed = false;
        } else if(!handlerNew.handleRequest(next, controller))  {
            LOGGER.info("illegal argument");
        }
//        switch(next)    {
//        case "q":
//            proceed = false;
//            break;
//        case "n":
//            controller.create();
//            break;
//        case "sS":
//            controller.create(9, 9, 10);
//            break;
//        case "sM":
//            controller.create(16, 16, 40);
//            break;
//        case "sL":
//            controller.create(16, 30, 99);
//            break;
//        case "u":
//            controller.undo();
//            break;
//        case "r":
//            controller.redo();
//            break;
//        default:
//            if (next.matches("[0-9][0-9]-[0-9][0-9]")) {
//                String[] parts = next.split("-");
//                controller.revealField(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
//            } else {
//                LOGGER.info("illegal argument");
//            }
//            if(controller.isGameOver())  {
//                LOGGER.info("GAME OVER!!!");
//                proceed = false;
//            }
//            if(controller.isVictory() == true)   {
//                LOGGER.info("Victory!!!");
//                proceed = false;
//            }
//        }
        return proceed;
    }

    @Override
    public void update(Event e) {
        paintTUI();
        if(controller.isGameOver())  {
            LOGGER.info("GAME OVER!!!");
        }
        if(controller.isVictory() == true)   {
            LOGGER.info("Victory!!!");
        }
    }
    
    public IHandler getChainOfResponsibility()    {
        return handlerNew;
    }
}
