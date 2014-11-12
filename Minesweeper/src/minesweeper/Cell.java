package minesweeper;


public class Cell {
	private int value;
	private int x, y;
	private int nMinesAround;

	public Cell(int x, int y, int value) {
		this.x = x;
		this.y = y;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int newValue) {
		this.value = newValue;
	}
	
	public int getNMinesAround() {
		return nMinesAround;
	}
	
	public void setNMinesAround(int value) {
		if (value > 8 || value < 0)
			return;
		this.nMinesAround = value;
	}
}
