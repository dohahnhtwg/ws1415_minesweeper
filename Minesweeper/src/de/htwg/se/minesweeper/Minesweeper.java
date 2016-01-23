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

import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

//import de.htwg.se.aview.gui.MinesweeperGUI;
import de.htwg.se.aview.tui.TextGUI;

public final class Minesweeper {
	
	private TextGUI webTui;
	
    private Minesweeper()   {
        PropertyConfigurator.configure("log4j.properties");
        
        Injector injector = Guice.createInjector(new MinesweeperModule());
        webTui = injector.getInstance(TextGUI.class);
    }

    public static Minesweeper getInstance() {
        return new Minesweeper();
    }

    public TextGUI getTui() {
		return webTui;
	}

	public static void main(String[] args) {
        Minesweeper.getInstance();
        
        Injector injector = Guice.createInjector(new MinesweeperModule());
        
        TextGUI tui = injector.getInstance(TextGUI.class);
        tui.paintTUI();
        
        boolean proceed = true;
        Scanner scanner = new Scanner(System.in);
        while (proceed) {
            proceed = tui.processInputLine(scanner.next());
        }
        scanner.close();
    }
}
