package de.htwg.se.model.impl;

import de.htwg.se.model.IStatistic;

import java.util.UUID;

public class Statistic implements IStatistic {

    private String id;
    private int gamesPlayed;
    private int gamesWon;
    private long timeSpent;
    private long minTime;

    public Statistic() {
        this.id = UUID.randomUUID().toString();
        this.gamesPlayed = this.gamesWon = 0;
        this.timeSpent = 0;
        this.minTime = Long.MAX_VALUE;
    }

    public Statistic(String id, Integer gamesPlayed, Integer gamesWon, Long timeSpent, Long minTime) {
        this.id = id;
        this.minTime = minTime;
        this.timeSpent = timeSpent;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    @Override
    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public int getGamesWon() {
        return this.gamesWon;
    }

    @Override
    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    @Override
    public long getTimeSpent() {
        return this.timeSpent;
    }

    @Override
    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public long getMinTime() {
        return this.minTime;
    }

    @Override
    public void setMinTime(Long minTime) {
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

    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
