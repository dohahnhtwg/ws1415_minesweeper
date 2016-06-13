package de.htwg.se.database.hibern8;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "statistictest")
public class HibernateStatistic implements Serializable{


    private String statid;
    private Integer gamesWon;
    private Long timeSpent;
    private Long minTime;
    private Integer gamesPlayed;
    private HibernateUser user;

    public HibernateStatistic() {}

    public HibernateStatistic(String statid, Integer gamesWon, Long timeSpent, Long minTime, Integer gamesPlayed, HibernateUser user) {
        this.statid = statid;
        this.gamesWon = gamesWon;
        this.timeSpent = timeSpent;
        this.minTime = minTime;
        this.gamesPlayed = gamesPlayed;
        this.user = user;
    }

    @Id
    public String getStatid() { return statid; }

    public void setStatid(String statid) {
        this.statid = statid;
    }

    @Column(name = "gamesPlayed")
    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Column(name = "minTime")
    public Long getMinTime() {
        return minTime;
    }

    public void setMinTime(Long minTime) {
        this.minTime = minTime;
    }

    @Column(name = "gamesWon")
    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    @Column(name = "timeSpent")
    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    @OneToOne(mappedBy = "statistic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public HibernateUser getUser() { return user; }

    public void setUser(HibernateUser user) { this.user = user; }
}
