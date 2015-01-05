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
    
}
