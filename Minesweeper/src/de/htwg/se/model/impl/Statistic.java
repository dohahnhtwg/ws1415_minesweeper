package de.htwg.se.model.impl;

import de.htwg.se.model.IStatistic;

class Statistic implements IStatistic {
    private int gamesPlayed;
    private int gamesWon;
    private long timeSpent;
    private long minTime;

    Statistic() {
        this.gamesPlayed = this.gamesWon = 0;
        this.timeSpent = 0;
        this.minTime = Long.MAX_VALUE;
    }
    @Override
    public int getPlayedGames() {
        return this.gamesPlayed;
    }

    @Override
    public int getWonGames() {
        return this.gamesWon;
    }

    @Override
    public long getPlayedTime() {
        return this.timeSpent;
    }

    @Override
    public long getMinTimePlayed() {
        return this.minTime;
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
}
