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
 * This interface represent a Controller for the Minesweeper Game.
 * @author Dominik Hahn & Pavel Kravetskiy
 *
 */
public interface IController extends IObservable {

    /**
     * @return the painting of a Field.
     */
    String getField();
    
    /**
     * Returns the actual playing field of the controller.
     * @return the actual playing Field.
     */
    IField getPlayingField();

    /**
     * Get current statistic instance
     * @return statistic
     */
    IStatistic getUserStatistic();

    /**
     * Start the timer
     */
    void startTimer();

    /**
     * Stop the timer
     */
    void stopTimer();

    /**
     * Get elapsed time since game start in seconds
     * @return time in millis
     */
    Long getCurrentTime();

    /**
     * Return whenever the game is started
     * @return true if started
     */
    boolean isStarted();

    /* Depracted section */

    void finishGame();

    boolean isVictory();

    boolean isGameOver();

    void create();

    void create(int x, int y, int z);

    void undo();

    void redo();

    @Deprecated
    boolean logIn(String name, String pass);

    @Deprecated
    boolean addNewAccount(String name, String pass);

    @Deprecated
    void revealField(int x, int y);
}
