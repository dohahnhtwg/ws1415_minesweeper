package de.htwg.se.database.hibern8;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "cell")
public class HibernateCell implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String cellid;

    @Column
    private Integer value;

    @Column
    private Boolean isRevealed;

    public HibernateCell(Integer value) {
        this.value = value;
    }

    public Boolean getIsRevealed() {
        return this.isRevealed;
    }

    public void seIstRevealed(Boolean revealed) {
        this.isRevealed = revealed;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getId() {
        return this.cellid;
    }

    public void setId(String id) {
        this.cellid = id;
    }
}
