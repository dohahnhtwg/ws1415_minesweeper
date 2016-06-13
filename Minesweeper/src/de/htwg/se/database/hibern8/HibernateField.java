package de.htwg.se.database.hibern8;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "fieldtest")
public class HibernateField implements Serializable {

    private String fieldid;
    private Integer nMines;
    private Integer lines;
    private Integer columns;
    private HibernateUser user;
    private Integer isVictory;
    private Integer isGameOver;
    private Set<HibernateCell> cells =  new HashSet<>(0);

    public HibernateField() {}

    public HibernateField(String fieldid, Integer nMines, Integer lines, Integer columns, HibernateUser user,
                          Integer isGameOver, Integer isVictory) {
        this.fieldid = fieldid;
        this.nMines = nMines;
        this.lines = lines;
        this.columns = columns;
        this.user = user;
        this.isGameOver = isGameOver;
        this.isVictory = isVictory;
    }

    @Id
    @Column(name = "fieldid", unique = true, nullable = false)
    public String getFieldid() {
        return fieldid;
    }

    public void setFieldid(String fieldid) {
        this.fieldid = fieldid;
    }

    @Column(name = "nMines")
    public Integer getnMines() {
        return nMines;
    }

    public void setnMines(Integer nMines) {
        this.nMines = nMines;
    }

    @Column(name = "ncolumns")
    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    @Column(name = "nlines")
    public Integer getLines() {
        return lines;
    }

    public void setLines(Integer lines) {
        this.lines = lines;
    }

    @OneToOne(mappedBy = "field", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public HibernateUser getUser() { return user; }

    public void setUser(HibernateUser user) { this.user = user; }

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    public Set<HibernateCell> getCells() {
        return cells;
    }

    public void setCells(Set<HibernateCell> cells) {
        this.cells = cells;
    }

    @Column(name = "isvictory")
    public Integer getIsVictory() {
        return isVictory;
    }

    public void setIsVictory(Integer isVictory) {
        this.isVictory = isVictory;
    }

    @Column(name = "isgameover")
    public Integer getIsGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(Integer isGameOver) {
        this.isGameOver = isGameOver;
    }
}
