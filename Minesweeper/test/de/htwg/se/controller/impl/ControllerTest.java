package de.htwg.se.controller.impl;

import static org.junit.Assert.*;

import de.htwg.se.aview.tui.handler.ConcreteHandlerNew;
import de.htwg.se.aview.tui.handler.ConcreteHandlerSize;
import de.htwg.se.aview.tui.handler.ConcreteHandlerUnReDo;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import de.htwg.se.aview.tui.IHandler;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.ICell;
import de.htwg.se.model.impl.Field;

public class ControllerTest {

    MainController controller;
    Field field;
    DataAccessObject databaseMock;
    
    @Before
    public void setUp() {
    	field = new Field();
    	databaseMock = mock(DataAccessObject.class);
        controller = new MainController(field, databaseMock);
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
        controller.create(2, 2, 4);
        controller.revealField(1, 1);
        controller.revealField(1, 2);
        assertFalse(field[1][2].getIsRevealed());
        controller.create(2, 2, 1);
        field = controller.getPlayingField().getField();
        if(field[1][1].getValue() == -1)    {
            controller.revealField(2, 1);
            controller.revealField(1, 1);
            assertTrue(field[1][1].getIsRevealed());
        } else {
            controller.revealField(1, 1);
            controller.revealField(2, 1);
            assertTrue(field[2][1].getIsRevealed());
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
    public void testUndoRedo()  {
        controller.create(5, 5, 0);
        controller.revealField(1, 1);
        controller.undo();
        ICell[][] field = controller.getPlayingField().getField();
        for (int i = 1; i < field.length - 1; i++)   {
            for (int j = 1; j < field[0].length - 1; j ++)    {
                assertFalse(field[i][j].getIsRevealed());
            }
        }
        controller.redo();
        field = controller.getPlayingField().getField();
        for (int i = 1; i < field.length - 1; i++)   {
            for (int j = 1; j < field[0].length - 1; j ++)    {
                assertTrue(field[i][j].getIsRevealed());
            }
        }
    }
    
    @Test
    public void testGetField()  {
        controller.create(2, 2, 4);
        String actual = controller.getField();
        String expected = "\nLine\n\n    1  -   - \n    2  -   - \n       1   2        Column\n";
        assertTrue(expected.equals(actual));
    }  
    
    @Test
    public void testConcreteHandler() {
        IHandler handlerNew = new ConcreteHandlerNew();
        IHandler handlerSize = new ConcreteHandlerSize();
        IHandler handlerUnReDo = new ConcreteHandlerUnReDo();
        IHandler handlerInput = new de.htwg.se.aview.tui.handler.ConcreteHandlerInput();
        
        handlerNew.setSuccesor(handlerSize);
        handlerSize.setSuccesor(handlerUnReDo);
        handlerUnReDo.setSuccesor(handlerInput);
        handlerInput.setSuccesor(null);
        
        assertEquals(handlerSize, handlerNew.getSuccesor());
        assertEquals(handlerUnReDo, handlerSize.getSuccesor());
        assertEquals(handlerInput, handlerUnReDo.getSuccesor());
        assertEquals(null, handlerInput.getSuccesor());
        
        assertTrue(handlerNew.handleRequest("n", controller, self()));
        assertFalse(handlerNew.handleRequest("test", controller, self()));
        
        assertTrue(handlerSize.handleRequest("sS", controller, self()));
        assertTrue(handlerSize.handleRequest("sM", controller, self()));
        assertTrue(handlerSize.handleRequest("sL", controller, self()));
        assertFalse(handlerSize.handleRequest("test", controller, self()));
        
        assertTrue(handlerUnReDo.handleRequest("u", controller, self()));
        assertTrue(handlerUnReDo.handleRequest("r", controller, self()));
        assertFalse(handlerUnReDo.handleRequest("test", controller, self()));

        assertTrue(handlerInput.handleRequest("01-01", controller, self()));
        assertFalse(handlerInput.handleRequest("test", controller, self()));
    }
    
    @Test
    public void finishGameShouldUpdateDatabase()	{
    	//Execute
    	controller.finishGame();
    	
    	//Assert
    	verify(databaseMock, times(1)).update(null);
    }
}
