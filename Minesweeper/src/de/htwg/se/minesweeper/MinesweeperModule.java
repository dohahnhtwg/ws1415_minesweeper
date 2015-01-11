package de.htwg.se.minesweeper;

import com.google.inject.AbstractModule;

import de.htwg.se.controller.IController;


public class MinesweeperModule extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(IController.class)
        .to(de.htwg.se.controller.impl.Controller.class);
        
    }

}
