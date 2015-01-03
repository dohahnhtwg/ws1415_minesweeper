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
