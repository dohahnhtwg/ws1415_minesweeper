package de.htwg.se.model.impl;

import de.htwg.se.model.IStatistic;

class Statistic implements IStatistic {
    private int gamesPlayed;
    private int gamesWon;
    private long timeSpent;
    private long minTime;

    Statistic() {
        this.gamesPlayed = this.gamesWon = 0;
        this.timeSpent = this.minTime = 0;
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
        }
        this.timeSpent += timeSpent;
        this.minTime = timeSpent < this.minTime ? timeSpent : this.minTime;
    }
}
