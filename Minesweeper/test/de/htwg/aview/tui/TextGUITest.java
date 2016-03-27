package de.htwg.aview.tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.aview.tui.Tui;
import de.htwg.se.controller.impl.Controller;
import de.htwg.se.model.impl.Field;

public class TextGUITest {

    Tui tui;
    Controller controller;
    Field field;
    
    @Before
    public void setUp() throws Exception {
    	field = new Field();
        controller = new Controller(field, null);
        tui = new Tui(controller);
    }
    
    @Test
    public void testProcessInputLine()   {
        assertFalse(tui.processInputLine("q"));
        
    }
}
