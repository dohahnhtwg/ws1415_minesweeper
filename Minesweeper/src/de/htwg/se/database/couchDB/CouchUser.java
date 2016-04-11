package de.htwg.se.database.couchDB;

/**
 * Created by GAAB on 11.04.2016.
 *
 */
public class CouchUser {

    private String name;
    private byte[] encryptedPassword;
    private byte[] salt;
    private CouchField playingField;
    private CouchStatistic statistic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CouchField getPlayingField() {
        return playingField;
    }

    public void setPlayingField(CouchField playingField) {
        this.playingField = playingField;
    }

    public CouchStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(CouchStatistic statistic) {
        this.statistic = statistic;
    }
}
