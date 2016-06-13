package de.htwg.se.database.hibern8;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "celltest")
public class HibernateCell implements Serializable {

    private String cellid;
    private Integer value;
    private Integer isRevealed;
    private String  fieldid;
    private Integer index;
    private HibernateField field;

    public HibernateCell() {}

    public HibernateCell(String cellid, Integer value, Integer isRevealed, Integer index, String fieldid, HibernateField field) {
        this.cellid = cellid;
        this.value = value;
        this.isRevealed = isRevealed;
        this.index = index;
        this.fieldid = fieldid;
        this.field = field;
    }

    @Id
    @Column(name = "cellid", unique = true, nullable = false)
    public String  getCellid() {
        return cellid;
    }

    public void setCellid(String  cellid) {
        this.cellid = cellid;
    }

    @Column(name = "value")
    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Column(name = "isRevealed")
    public Integer getIsRevealed() {
        return this.isRevealed;
    }

    public void setIsRevealed(Integer revealed) {
        this.isRevealed = revealed;
    }

    @Column(name = "idx")
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Column(name = "fieldid" )
    public String getFieldid() {
        return fieldid;
    }

    public void setFieldid(String fieldid) {
        this.fieldid = fieldid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fieldid", nullable = false, insertable = false, updatable = false)
    public HibernateField getField() { return field; }

    public void setField(HibernateField field) { this.field = field; }
}
