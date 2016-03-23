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

package de.htwg.se.minesweeper;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.se.aview.gui.MinesweeperGUI;
import de.htwg.se.aview.tui.Tui;

public final class Minesweeper {

    private Tui tui;
    private static Minesweeper instance = null;

    private Minesweeper() {
        
        Injector injector = Guice.createInjector(new MinesweeperModule());
        tui = injector.getInstance(Tui.class);
    }

    public static Minesweeper getInstance() {
        if (instance == null)	{
        	instance = new Minesweeper();
        }
        return instance;
    }

    public Tui getTui() {
		return tui;
	}

	public static void main(String[] args) {
        Minesweeper.getInstance();
        
        Injector injector = Guice.createInjector(new MinesweeperModule());
        
        Tui tui = injector.getInstance(Tui.class);
        new MinesweeperGUI(tui.getController());
        tui.paintTUI();
        
        boolean proceed = true;
        Scanner scanner = new Scanner(System.in);
        while (proceed) {
            proceed = tui.processInputLine(scanner.next());
        }
        scanner.close();
    }
}
