/* This file is part of Minesweeper.
 * 
 * Minesweeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minesweeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minesweeper.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.htwg.se.model.impl;

import java.util.Random;

import com.google.inject.Inject;

import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;

public class Field implements IField{
	
    private ICell[][] playingField;
    private static final int DEFDIMENS = 9;
    private static final int DEFNMINES = 10;
    private static final int BORDER = 2;
    private static final int THREE_CELL_LEN = 3;
    private int nMines;
    private int lines;
    private int columns;
    
    @Inject
    public Field()  {
        create(DEFDIMENS, DEFDIMENS, DEFNMINES);
    }
    
    public Field(int lines, int columns, int nMines)  {
        create(lines, columns, nMines);
    }
    
    
    public final void create(int lines, int columns, int nMines)    {
        this.lines = lines;
        this.columns = columns;
        this.nMines = nMines;
        if (lines < 1 || columns < 1 || nMines < 0)   {
            throw new IllegalArgumentException("lines, columns or nMines too small.");
        }
        playingField = new ICell[lines + BORDER][columns + BORDER];
        for (int i = 0; i < playingField.length; i++)   {
            for (int j = 0; j < playingField[0].length; j ++)    {
                playingField[i][j] = new Cell(0);
            }
        }
        fill(lines, columns);
    }
    
    private void fill(int lines, int columns) {
        Long timeforhash = System.nanoTime();
        int hash = timeforhash.hashCode();
        Random rnd = new Random(hash);
        for (int i = 0; i < nMines; i++)    {
            generateMines(lines, columns, rnd);
        }
        generateMinesAround();
    }
    
    private void generateMines(int lines, int columns, Random rnd)    {
        int x = rnd.nextInt(lines /*- 1*/) + 1;
        int y = rnd.nextInt(columns /*- 1*/) + 1;
        if (playingField[x][y].getValue() != -1)    {
            playingField[x][y].setValue(-1);
        } else  {
            generateMines(lines, columns, rnd);
        }
    }
    
    private void generateMinesAround() {
        for (int i = 1; i < playingField.length - 1; i++)   {
            for (int j = 1; j < playingField[0].length - 1; j ++)   {
                if (playingField[i][j].getValue() != -1)    {
                    playingField[i][j].setValue(nMinesAroundAPoint(i, j));
                }
            }
        }
    }
    
    private int nMinesAroundAPoint(int x, int y)    {
        int mines = 0;
        for (int i = 0; i < THREE_CELL_LEN; i++) {
            for (int j = 0; j < THREE_CELL_LEN; j++) {
                if (playingField[x - 1 + i][y - 1 + j].getValue() == -1)    {
                    mines++;
                }
            }
        }
        return mines;
    }
    
    public String toString()    {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(String.format("%5s\n", "Line\n"));
        for (int i = 1; i <= lines; i++) {
            sb.append(String.format("%5d", i));
            for (int j = 1; j <= columns; j++)  {
                sb.append(playingField[i][j].toString());
            }
            sb.append("\n");
        }
        sb.append("     ");
        for (int j = 1; j <= columns; j++)  {
            sb.append(String.format(" %2d ", j));
        }
        sb.append(String.format("       %s", "Column\n"));
        return sb.toString();
    }
    
    public int getLines()   {
        return lines;
    }


    public int getColumns() {
        return columns;
    }

    public int getnMines() {
        return nMines;
    }
    
    public ICell[][] getField() {
        return playingField;
    }
}
