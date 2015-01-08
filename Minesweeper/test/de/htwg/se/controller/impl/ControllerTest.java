package de.htwg.se.controller.impl;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.model.ICell;
import de.htwg.se.model.impl.Cell;

public class ControllerTest {

    Controller controller;
    
    @Before
    public void setUp() throws Exception {
        controller = new Controller();
    }
    
    @Test
    public void testVictory()   {
        assertFalse(controller.isVictory());
        controller.create(10, 10, 0);
        controller.revealField(5, 5);
        assertTrue(controller.isVictory());
    }
    
    @Test
    public void testGameOver()  {
        assertFalse(controller.isGameOver());
        controller.create(10, 10, 100);
        controller.revealField(5, 5);
        assertTrue(controller.isGameOver());
    }
    
    @Test
    public void testRevealField()   {
        controller.create(2, 2, 1);
        ICell[][] field = controller.getPlayingField().getField();
        if(field[1][1].getValue() == -1)    {
            controller.revealField(2, 1);
            assertTrue(field[2][1].getValue() == 1);
        } else {
            controller.revealField(1, 1);
            assertTrue(field[1][1].getValue() == 1);
        }
    }
    
    @Test
    public void testCreateStandard()    {
        controller.create(2, 7, 5);
        controller.create();
        assertEquals(2, controller.getPlayingField().getLines());
        assertEquals(7, controller.getPlayingField().getColumns());
        assertEquals(5, controller.getPlayingField().getnMines());
    }
    
    @Test
    public void testUndo()  {
        int x = 4;
        int y = 4;
        controller.create(x, y, 2);
        ICell[][] field = controller.getPlayingField().getField();
        ICell[][] testField = new ICell[x+2][y+2];
        for (int i = 0; i < field.length; i++)   {
            for (int j = 0; j < field[0].length; j ++)    {
                testField[i][j] = new Cell(field[i][j].getValue());
                testField[i][j].setRevealed(field[i][j].isRevealed());
                
            }
        }
        boolean test = Arrays.deepEquals(field, testField);
        assertTrue(test);
        
    }
    
}
