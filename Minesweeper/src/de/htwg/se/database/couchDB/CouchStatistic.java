package de.htwg.se.database.couchDB;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/**
 * Created by dohahn on 11.04.2016.
 *
 */
public class CouchStatistic extends CouchDbDocument {

    private static final long serialVersionUID = -6774088600239708899L;
    @TypeDiscriminator
    private String id;
    private int gamesPlayed;
    private int gamesWon;
    private long timeSpent;
    private long minTime;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
