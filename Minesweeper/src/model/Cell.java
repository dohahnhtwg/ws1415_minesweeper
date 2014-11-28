package model;


public class Cell {
	private int value;
	private boolean isRevealed = false;

	public Cell(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int newValue) {
		this.value = newValue;
	}

	public boolean isRevealed() {
		return isRevealed;
	}

	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}
	
	

}
