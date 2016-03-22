package de.htwg.se.database;

import de.htwg.se.model.IField;

public interface DataAccessObject {
	
	void create(IField field);
	IField read();
	void update(IField field);
	void delete();
	boolean contains();
}
