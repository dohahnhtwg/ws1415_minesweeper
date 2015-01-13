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

package de.htwg.se.util.observer;

public interface IObservable {

    /**
     * adds one Observer.
     * @param Observer to add.
     */
    void addObserver(IObserver s);

    /**
     * removes one Observer.
     * @param Observer to remove
     */
    void removeObserver(IObserver s);

    /**
     * removes all Observers.
     */
    void removeAllObservers();

    /**
     * notifies Observers.
     */
    void notifyObservers();

    /**
     * 
     * @param e is the Event.
     */
    void notifyObservers(Event e);
}
