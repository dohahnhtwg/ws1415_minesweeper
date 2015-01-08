package de.htwg.se.minesweeper;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import de.htwg.se.aview.gui.MinesweeperGUI;
import de.htwg.se.aview.tui.TextGUI;
import de.htwg.se.controller.IController;
import de.htwg.se.controller.impl.Controller;

public class Minesweeper {
    
    private static Minesweeper instance = null;
    private static Scanner scanner;
    private static TextGUI tui;
    private IController controller;
    private Minesweeper()   {
        PropertyConfigurator.configure("log4j.properties");

        controller = new Controller();
        new MinesweeperGUI(controller);
        tui = new TextGUI(controller);
        tui.paintTUI();
    }

    public static Minesweeper getInstance() {
        if (instance == null)   {
            instance = new Minesweeper();
        }
        return instance;
    }
    
    public static void main(String[] args) {
        Minesweeper.getInstance();
        
        boolean proceed = true;
        scanner = new Scanner(System.in);
        while (proceed) {
            proceed = tui.processInputLine(scanner.next());
        }
    }
}
