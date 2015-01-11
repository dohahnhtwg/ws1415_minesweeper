package de.htwg.se.minesweeper;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.se.aview.gui.MinesweeperGUI;
import de.htwg.se.aview.tui.TextGUI;
import de.htwg.se.controller.IController;

public final class Minesweeper {
    
    private static Minesweeper instance = null;
    private static Scanner scanner;
    private static TextGUI tui;

    private Minesweeper()   {
        
        PropertyConfigurator.configure("log4j.properties");

        Injector injector = Guice.createInjector(new MinesweeperModule());
        
        @SuppressWarnings("unused")
        IController controller = injector.getInstance(IController.class);
        @SuppressWarnings("unused")
        MinesweeperGUI gui = injector.getInstance(MinesweeperGUI.class);
        tui = injector.getInstance(TextGUI.class);
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
