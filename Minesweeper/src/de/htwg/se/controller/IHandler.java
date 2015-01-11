package de.htwg.se.controller;

public interface IHandler {
    
    void setSuccesor(IHandler successor);
    
    boolean handleRequest(String request, IController controller);
    
    IHandler getSuccesor();
    
}
