package de.htwg.se.model.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CellTest {
    
    private Cell cell;
    
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
        cell.setIsRevealed(true);
        assertTrue(cell.isRevealed());
        cell.setIsRevealed(false);
        assertFalse(cell.isRevealed());
    }
    
    @Test
    public void testToString()  {
        cell.setIsRevealed(true);
        cell.setValue(-1);
        assertTrue(cell.toString().equals("  * "));
        cell.setIsRevealed(false);
        cell.setValue(2);
        assertTrue(cell.toString().equals("  - "));
        cell.setIsRevealed(true);
        assertTrue(cell.toString().equals("  2 "));
    }
    
    @Test
    public void testEquals()  {
        Cell testCell = new Cell(1);
        assertTrue(cell.equals(cell));
        assertFalse(cell.equals(null));
        assertFalse(cell.equals(new Integer(0)));
        assertFalse(cell.equals(testCell));
        testCell.setIsRevealed(true);
        testCell.setValue(0);
        assertFalse(cell.equals(testCell));
        testCell.setIsRevealed(false);
        assertTrue(cell.equals(testCell));
        assertTrue(cell.hashCode() == cell.hashCode());
        testCell.setIsRevealed(true);
        cell.setIsRevealed(true);
    }

    @Test
    public void testId()    {
        assertNotNull(cell.getId());
        cell.setId("1");
        assertEquals("1", cell.getId());
    }
}
