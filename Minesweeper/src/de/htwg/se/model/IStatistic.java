package de.htwg.se.model;

public interface IStatistic {
    /**
     * Return number of all played games
     * @return int
     */
    int getGamesPlayed();

    /**
     * Set number of played games
     * @param gamesPlayed int
     */
    void setGamesPlayed(int gamesPlayed);

    /**
     * Return number of won games
     * @return int
     */
    int getGamesWon();

    /**
     * Set number of won games
     * @param gamesWon int
     */
    void setGamesWon(int gamesWon);

    /**
     * Return amount of played time
     * @return seconds
     */
    long getTimeSpent();

    /**
     * Set overall time spent
     * @param timeSpent seconds
     */
    void setTimeSpent(long timeSpent);
    /**
     * Return minimal time spent for a game
     * @return seconds
     */
    long getMinTime();

    /**
     * Set minimal time spent for a game
     * @param minTime seconds
     */
    void setMinTime(long minTime);

    /**
     * Update statistic according to given parameters
     * @param victory is game won
     * @param timeSpent time in seconds spent for the game
     */
    void updateStatistic(boolean victory, long timeSpent);

    /**
     * Return statistic id
     * @return id
     */
    int getId();

    /**
     * Set statistic id
     * @param id integer
     */
    void setId(int id);
}
