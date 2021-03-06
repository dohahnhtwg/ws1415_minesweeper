package de.htwg.se.database;

import de.htwg.se.model.IUser;

public interface DataAccessObject {

    void create(IUser user);
    IUser read(String username);
    void update(IUser user);
    void delete(IUser user);
    boolean contains(IUser user);
}
