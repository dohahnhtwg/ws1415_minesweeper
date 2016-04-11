package de.htwg.se.model.impl;

import de.htwg.se.model.IStatistic;

public class Statistic implements IStatistic {

    private int id;
    private int gamesPlayed;
    private int gamesWon;
    private long timeSpent;
    private long minTime;

    public Statistic() {
        this.gamesPlayed = this.gamesWon = 0;
        this.timeSpent = 0;
        this.minTime = Long.MAX_VALUE;
    }
    @Override
    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    @Override
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public int getGamesWon() {
        return this.gamesWon;
    }

    @Override
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    @Override
    public long getTimeSpent() {
        return this.timeSpent;
    }

    @Override
    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public long getMinTime() {
        return this.minTime;
    }

    @Override
    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    @Override
    public void updateStatistic(boolean victory, long timeSpent) {
        this.gamesPlayed += 1;
        if (victory) {
            this.gamesWon += 1;
            if (timeSpent < this.minTime) {
                this.minTime = timeSpent;
            }
        }
        this.timeSpent += timeSpent;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
