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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Observable implements IObservable {

    private List<IObserver> subscribers = new ArrayList<IObserver>(2);
    
    public void addObserver(IObserver s) {
        subscribers.add(s);
    }

    public void removeObserver(IObserver s) {
        subscribers.remove(s); 
    }

    public void removeAllObservers() {
        subscribers.clear();
        
    }

    public void notifyObservers() {
        notifyObservers(null);   
    }

    public void notifyObservers(Event e) {
        for (Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();) {
            IObserver observer = iter.next();
            observer.update(e);
        } 
    }

}
