package de.htwg.se.database.hibern8;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "statistic")
public class HibernateStatistic implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer statid;

    @Column
    private Integer gamesWon;

    @Column
    private Long timeSpent;

    @Column
    private Long minTime;

    @Column
    private Integer gamesPlayed;

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Long getMinTime() {
        return minTime;
    }

    public void setMinTime(Long minTime) {
        this.minTime = minTime;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Integer getStatid() {
        return statid;
    }

    public void setStatid(Integer statid) {
        this.statid = statid;
    }
}
