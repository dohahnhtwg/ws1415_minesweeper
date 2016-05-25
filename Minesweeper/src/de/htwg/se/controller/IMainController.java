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

package de.htwg.se.controller;

import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.util.observer.IObservable;

/**
 * This interface represent a MainController for the Minesweeper Game.
 * @author Dominik Hahn & Pavel Kravetskiy
 * TODO Removed Interfaces, communication only over actors
 */
public interface IMainController {

    /* Depracted section use actors */

    @Deprecated
    IField getPlayingField();

    @Deprecated
    void finishGame();

    @Deprecated
    boolean isVictory();

    @Deprecated
    boolean isGameOver();

    @Deprecated
    void create();

    @Deprecated
    void create(int x, int y, int z);

    @Deprecated
    void undo();

    @Deprecated
    void redo();

    @Deprecated
    boolean logIn(String name, String pass);

    @Deprecated
    boolean addNewAccount(String name, String pass);

    @Deprecated
    void revealField(int x, int y);

    @Deprecated
    Long getCurrentTime();

    @Deprecated
    IStatistic getUserStatistic();
}
