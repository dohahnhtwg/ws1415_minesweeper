import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestClass {

	Playing_field playingField;
	int x = 5;
	int y = 10;
	
	public void main()	{
		setUp();
		testCreatePlayingField();
		
	}
	
	@Before
	public void setUp()	{
		playingField = new Playing_field(x, y);
	}
	
	@Test
	public void testCreatePlayingField()	{
		for(int i = 0; i < y; i++)	{
			for(int j = 0; j < x; j++)	{
				assertNotNull(playingField.getFields()[j][i]);
			}
		}
	}
}
