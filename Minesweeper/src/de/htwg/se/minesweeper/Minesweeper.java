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

import akka.actor.ActorSystem;
import akka.actor.Props;

public final class Minesweeper {

    private static Minesweeper instance = null;

    private Minesweeper() {

    }

    static Minesweeper getInstance() {
        if (instance == null)	{
        	instance = new Minesweeper();
        }
        return instance;
    }

	public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("Minesweeper");
        system.actorOf(Props.create(MinesweeperActor.class), "minesweeper");
        system.awaitTermination();
    }
}

