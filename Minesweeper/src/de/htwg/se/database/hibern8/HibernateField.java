package de.htwg.se.database.hibern8;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "field")
public class HibernateField implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fieldid;

    @ManyToOne
    @JoinColumn(name = "cellid")
    private HibernateCell playingField; // FIXME

    @Column
    private Integer nMines;

    @Column
    private Integer lines;

    @Column
    private Integer columns;

    public HibernateField() {

    }

    public Integer getFieldid() {
        return fieldid;
    }

    public void setFieldid(Integer fieldid) {
        this.fieldid = fieldid;
    }

    public HibernateCell getPlayingField() {
        return playingField;
    }

    public void setPlayingField(HibernateCell playingField) {
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
