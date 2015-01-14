package de.htwg.se.controller.logwrapper;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.se.controller.IController;
import de.htwg.se.model.IField;
import de.htwg.se.util.observer.Event;
import de.htwg.se.util.observer.IObserver;
import de.htwg.se.util.observer.Observable;



@Singleton
public class Controller extends Observable implements IController {

    private Logger logger = Logger
            .getLogger("de.htwg.se.controller.wrapperimpl");
    private IController realController;
    private long startTime;
    
    @Inject
    public Controller() {
        realController = new de.htwg.se.controller.impl.Controller();
    }
    
    private void pre() {
        logger.debug("Controller method " + getMethodName(1) + " was called.");
        startTime = System.nanoTime();
    }

    private void post() {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        logger.debug("Controller method " + getMethodName(1)
                + " was finished in " + duration + " nanoSeconds.");
    }

    private static String getMethodName(final int depth) {
        final StackTraceElement[] stack = Thread.currentThread()
                .getStackTrace();
        return stack[2 + depth].getMethodName();
    }
    
    public void create() {
        pre();
        realController.create();
        post();
    }

    public void create(int lines, int columns, int nMines) {
        pre();
        realController.create(lines, columns, nMines);
        post();
    }

    public boolean isVictory() {
        return realController.isVictory();
    }

    public boolean isGameOver() {
        return realController.isGameOver();
    }

    @Override
    public void revealField(int x, int y) {
        pre();
        realController.revealField(x, y);;
        post();
    }

    @Override
    public void undo() {
        pre();
        realController.undo();
        post();
    }

    public void redo() {
        pre();
        realController.redo();
        post();    
    }

    public String getField() {
        return realController.getField();
    }

    public IField getPlayingField() {
        return realController.getPlayingField();
    }
    
    @Override
    public void addObserver(IObserver s) {
        pre();
        realController.addObserver(s);
        post();
    }
    
    @Override
    public void removeObserver(IObserver s) {
        pre();
        realController.removeObserver(s);
        post();
    }
    
    @Override
    public void removeAllObservers() {
        pre();
        realController.removeAllObservers();
        post();
    }
    
    @Override
    public void notifyObservers() {
        pre();
        realController.notifyObservers();
        post();
    }
    
    @Override
    public void notifyObservers(Event e) {
        pre();
        realController.notifyObservers(e);
        post();
    }
}
