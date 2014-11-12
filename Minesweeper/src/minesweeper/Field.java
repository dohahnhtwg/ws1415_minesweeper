package minesweeper;

import java.util.Random;

public class Field {
	private Cell field[][];
	private int nMines;
	public Field(int x, int y, int nMines) {
		if (x < 1 || y < 1 || nMines < 0) {
			return;
		}
		this.nMines = nMines;
		fill(x, y);
	}
	
	private void fill(int x, int y) {
		Long timeforhash = System.nanoTime();
		int hash = timeforhash.hashCode();
		Random rnd = new Random(hash);
		for (int k = 0; k < nMines; k++) {
			generateCell(x, y, rnd);
		}
	}
	private void generateCell(int x, int y, Random rnd) {
		while (true) {
			int row = rnd.nextInt(x);
			int col = rnd.nextInt(y);
			if (field[row][col].getValue() == 1) {
				continue;
			} else {
				field[row][col] = new Cell(x, y, 1);
				break;
			}
		}
	}
}
