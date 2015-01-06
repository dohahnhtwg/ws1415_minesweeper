package de.htwg.se.model.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.model.impl.Field;

public class FieldTest {

    Field field;
    
    @Before
    public void setUp() throws Exception {
        field = new Field();
    }
    
    @Test
    public void testGetLines()  {
        field.create(2, 2, 1);
        assertEquals(2, field.getLines());
    }
    
    @Test
    public void testGetColumns()  {
        field.create(2, 2, 1);
        assertEquals(2, field.getColumns());
    }
    
    @Test
    public void testGetnMines()  {
        field.create(2, 2, 1);
        assertEquals(1, field.getnMines());
    }
    
    @Test
    public void testGetField()  {
        field.create(2, 2, 0);
        Cell[][] actualArray = new Cell[2][2];
        actualArray[0][0] = new Cell(0);
        actualArray[0][1] = new Cell(0);
        actualArray[1][0] = new Cell(0);
        actualArray[1][1] = new Cell(0);
        assertEquals(actualArray[0][0].getValue(), field.getField()[0][0].getValue());
        assertEquals(actualArray[0][1].getValue(), field.getField()[0][1].getValue());
        assertEquals(actualArray[1][0].getValue(), field.getField()[1][0].getValue());
        assertEquals(actualArray[1][1].getValue(), field.getField()[1][1].getValue());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCreateColumn()  {
        field.create(0, 2, 1);
        assertEquals(1, field.getnMines());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCreateLine()  {
        field.create(2, 0, 1);
        assertEquals(1, field.getnMines());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCreateMines()  {
        field.create(2, 2, -1);
        assertEquals(1, field.getnMines());
    }
    
    @Test
    public void testFieldConstructor()  {
        field = new Field(9, 9, 10);
        assertEquals(9, field.getColumns());
        assertEquals(9, field.getLines());
        assertEquals(10, field.getnMines());
    }
    
    @Test
    public void testToString()  {
        field = new Field(2, 2, 4);
        String actual = field.toString();
        String expected = "\n Line\n    1  -   - \n    2  -   - \n       1   2 \n       Column\n";
        System.out.println(actual);
        System.out.println(expected);
        assertTrue(expected.equals(actual));
    }
    
}
