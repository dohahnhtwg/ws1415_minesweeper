package de.htwg.se.model.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StatisticTest {
    Statistic statistic;

    @Before
    public void setUp() throws Exception {
        statistic = new Statistic();
    }

    @Test
    public void testTimePlayed() {
        statistic.updateStatistic(true, 100);
        assertEquals(100, statistic.getTimeSpent());
    }

    @Test
    public void testMinTimePlayed() {
        statistic.updateStatistic(true, 70);
        statistic.updateStatistic(false, 10);
        statistic.updateStatistic(true, 40);
        assertEquals(40, statistic.getMinTime());
    }

    @Test
    public void testGamesWon() {
        statistic.updateStatistic(true, 30);
        statistic.updateStatistic(true, 40);
        assertEquals(2, statistic.getGamesWon());
    }

    @Test
    public void testGamesPlayed() {
        statistic.updateStatistic(false, 50);
        statistic.updateStatistic(false, 50);
        assertEquals(2, statistic.getGamesPlayed());
    }
}
