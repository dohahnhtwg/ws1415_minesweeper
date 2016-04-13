package de.htwg.se.database.hibern8;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "field")
public class HibernateField implements Serializable {

    @Id
    @Column(name = "fieldid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String fieldid;

    @OneToMany(mappedBy = "field")
    @Column(name = "cells")
    private List<HibernateCell> playingField;

    @Column(name = "nMines")
    private Integer nMines;

    @Column(name = "lines")
    private Integer lines;

    @Column(name = "columns")
    private Integer columns;

    public HibernateField() {

    }

    public String getFieldid() {
        return fieldid;
    }

    public void setFieldid(String fieldid) {
        this.fieldid = fieldid;
    }

    public List<HibernateCell> getPlayingField() {
        return playingField;
    }

    public void setPlayingField(List<HibernateCell> playingField) {
        this.playingField = playingField;
    }

    public Integer getnMines() {
        return nMines;
    }

    public void setnMines(Integer nMines) {
        this.nMines = nMines;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getLines() {
        return lines;
    }

    public void setLines(Integer lines) {
        this.lines = lines;
    }
}
