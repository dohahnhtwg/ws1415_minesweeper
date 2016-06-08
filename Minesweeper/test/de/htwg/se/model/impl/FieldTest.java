package de.htwg.se.model.impl;

import static org.junit.Assert.*;

import de.htwg.se.model.ICell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.htwg.se.model.impl.Field;
import org.junit.rules.ExpectedException;

public class FieldTest {

    private Field field;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        field = new Field();
    }
    
    @Test
    public void testGetLines()  {
        field = new Field(2, 2, 1);
        assertEquals(2, field.getLines());
    }
    
    @Test
    public void testGetColumns()  {
        field = new Field(2, 2, 1);
        assertEquals(2, field.getColumns());
    }
    
    @Test
    public void testGetnMines()  {
        field = new Field(2, 2, 1);
        assertEquals(1, field.getnMines());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCreateColumn()  {
        field = new Field(0, 2, 1);
        assertEquals(1, field.getnMines());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCreateLine()  {
        field = new Field(2, 0, 1);
        assertEquals(1, field.getnMines());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentCreateMines()  {
        field = new Field(2, 2, -1);
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
        ICell[][] actualField = new ICell[4][4];
        for(int i = 0; i < actualField.length; i++)  {
            for(int j = 0; j < actualField[i].length; j++)  {
                actualField[i][j] = new Cell(0);
            }
        }
        field.setPlayingField(actualField);
        String actual = field.toString();
        String expected = "\nLine\n\n    1  -   - \n    2  -   - \n       1   2        Column\n";
        System.out.println(actual);
        System.out.println(expected);
        assertTrue(expected.equals(actual));
    }

    @Test
    public void testFieldId()   {
        field = new Field();
        assertNotNull(field.getFieldID());
        field = new Field(2, 2, 4);
        assertNotNull(field.getFieldID());
        field.setFieldID("1");
        assertEquals("1", field.getFieldID());
    }

    @Test(expected=IllegalArgumentException.class)
    public void setFieldShouldThrowIllegalArgumentExceptionIfFieldNull()   {
        field.setPlayingField(null);
    }

    @Test
    public void testIsVictory() {
        field.setIsVictory(true);
        assertEquals(true, field.isVictory());
    }

    @Test
    public void testIsGameOver()    {
        field.setIsGameOver(true);
        assertTrue(field.isGameOver());
    }

    @Test
    public void setFieldShouldThrowIllegalArgumentExceptionIfNotEnoughColumns() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Field needs at least one column and two border columns");
        field = new Field();
        ICell[][] testField = new ICell[3][2];
        for(int i = 0; i < testField.length; i++)  {
            for(int j = 0; j < testField[i].length; j++)  {
                testField[i][j] = new Cell(0);
            }
        }
        field.setPlayingField(testField);
    }

    @Test
    public void setFieldShouldThrowIllegalArgumentExceptionIfNotEnoughLines() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Field needs at least one line and two border lines");
        field = new Field();
        ICell[][] testField = new ICell[2][3];
        for(int i = 0; i < testField.length; i++)  {
            for(int j = 0; j < testField[i].length; j++)  {
                testField[i][j] = new Cell(0);
            }
        }
        field.setPlayingField(testField);
    }

    @Test
    public void setFieldShouldSetColumnsLinesAndMines() {
        ICell[][] testField = new ICell[4][4];
        testField[1][1] = new Cell(-1);
        testField[1][2] = new Cell(2);
        testField[2][1] = new Cell(2);
        testField[2][2] = new Cell(-1);
        field.setPlayingField(testField);
        assertEquals(2, field.getColumns());
        assertEquals(2, field.getLines());
        assertEquals(2, field.getnMines());
    }

    @Test
    public void testGetField() {
        ICell[][] testField = new ICell[4][4];
        testField[1][1] = new Cell(-1);
        testField[1][2] = new Cell(2);
        testField[2][1] = new Cell(2);
        testField[2][2] = new Cell(-1);
        field.setPlayingField(testField);
        assertEquals(testField, field.getPlayingField());
    }
}
