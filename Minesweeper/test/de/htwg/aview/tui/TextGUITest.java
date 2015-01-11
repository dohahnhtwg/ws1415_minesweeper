package de.htwg.aview.tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.aview.tui.TextGUI;
import de.htwg.se.controller.impl.Controller;

public class TextGUITest {

    TextGUI tui;
    Controller controller;
    
    @Before
    public void setUp() throws Exception {
        controller = new Controller();
        tui = new TextGUI(controller);
    }
    
    @Test
    public void testProcessInputLine()   {
        assertFalse(tui.processInputLine("q"));
        
    }
}
