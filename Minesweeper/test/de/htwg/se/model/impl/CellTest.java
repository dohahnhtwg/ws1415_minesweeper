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
    
    @Test
    public void testToString()  {
        cell.setRevealed(true);
        cell.setValue(-1);
        assertTrue(cell.toString().equals("  * "));
        cell.setRevealed(false);
        cell.setValue(2);
        assertTrue(cell.toString().equals("  - "));
        cell.setRevealed(true);
        assertTrue(cell.toString().equals("  2 "));
    }
    
    @Test
    public void testEquals()  {
        Cell testCell = new Cell(1);
        assertTrue(cell.equals(cell));
        assertFalse(cell.equals(null));
        assertFalse(cell.equals(new Integer(0)));
        assertFalse(cell.equals(testCell));
        testCell.setRevealed(true);
        testCell.setValue(0);
        assertFalse(cell.equals(testCell));
        testCell.setRevealed(false);
        assertTrue(cell.equals(testCell));
        assertTrue(cell.hashCode() == testCell.hashCode());
        testCell.setRevealed(true);
        cell.setRevealed(true);
        assertTrue(cell.hashCode() == testCell.hashCode());
    }
}
