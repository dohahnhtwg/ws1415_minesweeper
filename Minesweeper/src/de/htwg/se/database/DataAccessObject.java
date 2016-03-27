package de.htwg.se.database;

import de.htwg.se.model.IUser;

public interface DataAccessObject {
	
	void create(IUser user);
	IUser read(String username, String password);
	void update(IUser user);
	void delete();
	boolean contains(IUser user);
}
