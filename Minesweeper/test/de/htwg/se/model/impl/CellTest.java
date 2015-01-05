package de.htwg.se.model.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.model.impl.Cell;

public class CellTest {
    
    Cell cell;
    
    @Before
    public void setUp() throws Exception {
        cell = new Cell(0);
    }
    
    @Test
    public void testGetValue()   {
        cell.setValue(3);
        assertEquals(3, cell.getValue());
        cell.setValue(0);
        assertEquals(0, cell.getValue());
    }
    
    @Test
    public void testIsRevealed()    {
        cell.setRevealed(true);
        assertTrue(cell.isRevealed());
        cell.setRevealed(false);
        assertFalse(cell.isRevealed());
    }
    
    public void testToString()  {
        cell.setRevealed(true);
        cell.setValue(-1);
        String testString = " *";
        assertEquals(testString, cell.toString());
        cell.setValue(2);
        testString = " 2";
        assertEquals(testString, cell.toString());
        cell.setRevealed(false);
        testString = " -";
        assertEquals(testString, cell.toString());
    }

}
