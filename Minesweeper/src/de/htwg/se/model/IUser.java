package de.htwg.se.model;

public interface IUser {
    String getName();
    void setName(String name);
    void setPassword(String password);
    String getEncryptedPassword();
    boolean authenticate(String name, String password);
    IStatistic getStatistic();
    void setStatistic(IStatistic statistic);
}
