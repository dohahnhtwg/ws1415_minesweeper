package de.htwg.se.minesweeper;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import de.htwg.se.aview.TextGUI;
import de.htwg.se.controller.Field;

public class Minesweeper {
    
    private static Minesweeper instance = null;
    private static Scanner scanner;
    private static TextGUI tui;
    private Field field;
    private int fieldLen = 20;
    private int nMines = 5;
    private Minesweeper()   {
        PropertyConfigurator.configure("log4j.properties");
        
        field = new Field(fieldLen, fieldLen, nMines);
        tui = new TextGUI(field);
        tui.paintField(field);
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
