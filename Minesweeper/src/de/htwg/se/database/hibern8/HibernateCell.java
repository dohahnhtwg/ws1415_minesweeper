package de.htwg.se.database.hibern8;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "hibernatecell")
public class HibernateCell implements Serializable {

    @Id
    @Column(name = "cellid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cellid;

    @Column(name = "value")
    private Integer value;

    @Column(name = "isRevealed")
    private Boolean isRevealed;

    @ManyToOne
    @JoinColumn(name = "fieldid")
    private HibernateField field;

    public HibernateCell(Integer value) {
        this.value = value;
    }

    public HibernateCell() {
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
        return Integer.toString(this.cellid);
    }

    public void setId(String id) {
        this.cellid = Integer.parseInt(id) + 1;
    }
}
