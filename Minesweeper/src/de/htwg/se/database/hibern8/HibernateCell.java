package de.htwg.se.database.hibern8;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "cell")
public class HibernateCell implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cellid;

    @Column
    private Integer value;

    @Column
    private Boolean isRevealed;

    @ManyToOne
    @JoinColumn(name = "fieldid")
    public HibernateField field;

    public HibernateCell(Integer value) {
        this.value = value;
    }

    public HibernateCell() {
    }

    public Boolean getRevealed() {
        return this.isRevealed;
    }

    public void setRevealed(Boolean revealed) {
        this.isRevealed = revealed;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return this.cellid;
    }

    public void setId(Integer id) {
        this.cellid = id;
    }
}
