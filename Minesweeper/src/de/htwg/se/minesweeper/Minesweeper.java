package de.htwg.se.minesweeper;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import de.htwg.se.aview.tui.TextGUI;
import de.htwg.se.controller.IField;
import de.htwg.se.controller.impl.Field;

public class Minesweeper {
    
    private static Minesweeper instance = null;
    private static Scanner scanner;
    private static TextGUI tui;
    private IField field;
    private final int fieldLen = 9;
    private final int nMines = 10;
    private Minesweeper()   {
        PropertyConfigurator.configure("log4j.properties");
        
        field = new Field(fieldLen, fieldLen, nMines);
        tui = new TextGUI(field);
        tui.paintField();
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
