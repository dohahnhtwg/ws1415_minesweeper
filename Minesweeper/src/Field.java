package minesweeper;

import java.util.Random;

public class Field {
	private Cell field[][];

	public Cell[][] getField() {
		return field;
	}


	private int nMines;
	public Field(int x, int y, int nMines) {
		if (x < 1 || y < 1 || nMines < 0) {
			return;
		}
		field = new Cell[x + 2][y + 2];
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field[0].length; j ++) {
				field[i][j] = new Cell(0);
			}
		this.nMines = nMines;
		fill(x, y);
	}

	private void fill(int x, int y) {
		Long timeforhash = System.nanoTime();
		int hash = timeforhash.hashCode();
		Random rnd = new Random(hash);
		for (int k = 0; k < nMines; k++) {
			generateMines(x, y, rnd);
		}
		generateMinesAround();
	}

	private void generateMines(int x, int y, Random rnd) {
		while (true) {
			int row = rnd.nextInt(x - 1) + 1;
			int col = rnd.nextInt(y - 1) + 1;
			if (field[row][col].getValue() == -1) {
				continue;
			} else {
				field[row][col].setValue(-1);
				break;
			}
		}
	}
	
	private void generateMinesAround() {
		for (int i = 1; i < field.length - 1; i++) {
			for (int j = 1; j < field[0].length - 1; j ++) {
				if (field[i][j].getValue() != -1) {
					field[i][j].setValue(nMinesAroundAPoint(i, j));
				}
			}
		}
	}
	
	
	private int nMinesAroundAPoint(int x, int y) {
		int mines = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (field[x - 1 + i][y - 1 + j].getValue() == -1)
					mines++;
			}
		return mines;
	}
}
