package de.htwg.se.model;

public interface IStatistic {
    /**
     * Return number of all played games
     * @return int
     */
    int getPlayedGames();

    /**
     * Return number of won games
     * @return int
     */
    int getWonGames();

    /**
     * Return amount of played time
     * @return seconds
     */
    long getPlayedTime();

    /**
     * Return minimal time spent for a game
     * @return seconds
     */
    long getMinTimePlayed();

    /**
     * Update statistic according to given parameters
     * @param victory is game won
     * @param timeSpent time in seconds spent for the game
     */
    void updateStatistic(boolean victory, long timeSpent);
}
