package de.htwg.se.database.hibern8;

import org.hibernate.annotations.*;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "usertest")
public class HibernateUser implements Serializable {

    private String userid;
    private String name;
    private byte[] encryptedPassword;
    private byte[] salt;
    private String algorithm;
    private String fieldid;
    private HibernateField field;
    private String statid;
    private HibernateStatistic statistic;

    public HibernateUser() {}

    public HibernateUser(String userid, String name, byte[] encryptedPassword, byte[] salt, String algorithm,
                         HibernateField field, HibernateStatistic statistic, String statid) {
        this.userid = userid;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
        this.algorithm = algorithm;
        this.field = field;
        this.statistic = statistic;
        this.statid = statid;
    }

    @Id
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "encryptedPassword")
    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Column(name = "salt")
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Column(name = "pwdalgorithm")
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Column(name = "username")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "fieldid", unique = true)
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "fieldtest"))
    public String getFieldid() { return fieldid; }

    public void setFieldid(String fieldid) { this.fieldid = fieldid; }

    @Column(name = "statid", unique = true)
    @GeneratedValue(generator = "gen1")
    @GenericGenerator(name = "gen1", strategy = "foreign", parameters = @Parameter(name = "property", value = "statistictest"))
    public String getStatid() { return statid; }

    public void setStatid(String statid) { this.statid = statid; }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public HibernateField getField() {
        return field;
    }

    public void setField(HibernateField field) {
        this.field = field;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public HibernateStatistic getStatistic() { return statistic; }

    public void setStatistic(HibernateStatistic statistic) { this.statistic = statistic; }
}
