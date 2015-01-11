package de.htwg.se.controller;

public interface IHandler {
    
    public void setSuccesor(IHandler successor);
    
    public boolean handleRequest(String request, IController controller);
    
    public IHandler getSuccesor();
    
}
