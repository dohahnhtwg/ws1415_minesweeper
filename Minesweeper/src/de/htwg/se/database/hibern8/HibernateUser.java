package de.htwg.se.database.hibern8;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class HibernateUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private byte[] encryptedPassword;

    @Column(name = "salt")
    private byte[] salt;

    @Column
    private String algorithm;

    @OneToOne
    @JoinColumn(name = "fieldid")
    private HibernateField field;

    @OneToOne
    @JoinColumn(name = "statid")
    private HibernateStatistic statistic;

    public HibernateUser() {
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public HibernateField getField() {
        return field;
    }

    public void setField(HibernateField field) {
        this.field = field;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HibernateStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(HibernateStatistic statistic) {
        this.statistic = statistic;
    }
}
