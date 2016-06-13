package de.htwg.se.model.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.User;

import java.security.NoSuchAlgorithmException;

public class UserTest {

	private IUser user;
	
	@Before
    public void setUp()	{
        user = new User("testuser", "1234");
    }
	
	@Test
	public void testSetPlayingField()	{
		//Arrange
		IField field = new Field();
		
		//Execute
		user.setPlayingField(field);
		
		//Assert
		assertSame(field, user.getPlayingField());
	}
	
	@Test
	public void testSetStatistic()	{
		//Arrange
		IStatistic stat = new Statistic();
		
		//Execute
		user.setStatistic(stat);
		
		//Assert
		assertSame(stat, user.getStatistic());
	}
	
	@Test
	public void userSuccessfullyAuthenticate()	{
		//Arrange
		String userName = "testuser";
		String password = "1234";
		
		//Execute
		boolean success = user.authenticate(userName, password);
		
		//Assert
		assertTrue(success);
	}
	
	@Test
	public void authenticateWithWrongUserName()	{
		//Arrange
		String userName = "test";
		String password = "1234";
		
		//Execute
		boolean success = user.authenticate(userName, password);
		
		//Assert
		assertFalse(success);
	}
	
	@Test
	public void authenticateWithWrongPassword()	{
		//Arrange
		String userName = "testuser";
		String password = "wrong";
		
		//Execute
		boolean success = user.authenticate(userName, password);
		
		//Assert
		assertFalse(success);
	}
	
	@Test
	public void authenticateShouldThrowExceptionWithInvalidAlgo(){
		//Arrange
		String userName = "testuser";
		String password = "1234";
		String invalidAlgo = "something";
		
		user.setAlgorithm(invalidAlgo);
		
		//Execute
		boolean success = user.authenticate(userName, password);
		
		//Assert
		assertFalse(success);
	}
	
	@Test
	public void setUserShouldChangeUserName()	{
		//Arrange
		String userName = "anotherName";
		
		//Execute
		user.setName(userName);
		
		//Assert
		assertSame(user.getName(), userName);
	}

	@Test
	public void newUserNeedId()	{
		user = new User();
		assertNotNull(user.getId());
	}

	@Test
	public void testSetId()	{
		user.setId("1");
		assertEquals("1", user.getId());
	}

	@Test
	public void testSetSalt()	{
		user.setSalt("test".getBytes());
		assertEquals("test", new String(user.getSalt()));
	}

	@Test
	public void testSetAlgorithm()	{
		user.setAlgorithm("test");
		assertEquals("test", user.getAlgorithm());
	}

	@Test
	public void testSetEncryptedPassword()	{
		user.setEncryptedPassword("test".getBytes());
		assertEquals("test", new String(user.getEncryptedPassword()));
	}
}
