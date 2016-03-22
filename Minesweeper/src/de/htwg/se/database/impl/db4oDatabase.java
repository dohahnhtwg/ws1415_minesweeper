package de.htwg.se.database.impl;

import java.util.List;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.IField;
import de.htwg.se.model.impl.Field;

public class db4oDatabase implements DataAccessObject {
	
	private final String DB4OFILENAME = ".\\db4oDatabase";
	
	public void create(IField field) {
		ObjectContainer db = Db4o.openFile(DB4OFILENAME);
		try {
			db.store(field);
		}
		finally {
			db.close();
		}
	}

	public IField read() {
		ObjectContainer db = Db4o.openFile(DB4OFILENAME);
		
		try {
			List<Field> fields = db.query(new Predicate<Field>() {
				public boolean match(Field field) {
					return field.getiD() == 0;
				}
			});
			return fields.get(0);
		}
		finally {
			db.close();
		}
	}

	public void update(IField field) {
		ObjectContainer db = Db4o.openFile(DB4OFILENAME);
		try {
			List<Field> fields = db.query(new Predicate<Field>() {
				public boolean match(Field field) {
					return field.getiD() == 0;
				}
			});
			IField old = fields.get(0);
			old = field;
			db.store(old);
			
		}
		finally {
		 db.close();
		}
	}

	public void delete() {


	}
	
	public boolean contains()	{
		ObjectContainer db = Db4o.openFile(DB4OFILENAME);
		
		try {
			List<Field> fields = db.query(new Predicate<Field>() {
				public boolean match(Field field) {
					return field.getiD() == 0;
				}
			});
			return !(fields.size() == 0);
		}
		finally {
			 db.close();
		}
		
	}

}
