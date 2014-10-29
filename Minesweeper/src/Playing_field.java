/* The whole playing field */
public class Playing_field {
	
	Field [][] fields;		//Field Matrix with [x][y]
	
	/* length and high define how big the playing field is */
	public Playing_field(int length, int hight)	{
		fields = new Field[length][hight];
		createPlayingField(length, hight);
	}
	
	/* Creates new Field elements and fill them in the field Matrix */
	private void createPlayingField(int length, int hight)	{
		for(int i = 0; i < length; i++)	{
			for(int j = 0; j < hight; j++)	{
				fields[i][j] =  new Field(i, j);
			}
		}
	}
	
	/* Spread mines over the playing field 
	private void setMines(int numberMines)	{
		
	}
	*/
	public Field[][] getFields() {
		return fields;
	}
	
	
}
