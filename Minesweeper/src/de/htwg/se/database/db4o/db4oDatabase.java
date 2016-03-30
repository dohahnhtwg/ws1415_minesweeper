package de.htwg.se.database.db4o;

import java.util.List;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.foundation.NotImplementedException;
import com.db4o.query.Predicate;
import com.google.inject.Inject;

import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.User;

public class db4oDatabase implements DataAccessObject {
    
    private final String DB4OFILENAME = ".\\db4oDatabase";

    @Inject
    public db4oDatabase()    {
        
    }
    
    @Override
    public void create(IUser user) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded    
                .newConfiguration(), DB4OFILENAME);
        try {
            db.store(user);
        }
        finally {
            db.close();
        }
    }

    @Override
    public IUser read(final String username, final String password) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded    
                .newConfiguration(), DB4OFILENAME);
        
        try {
            List<User> users = db.query(new Predicate<User>() {

                private static final long serialVersionUID = 1L;

                public boolean match(User user) {
                    return user.getName().equals(username);
                }
            });
            if(users.isEmpty())    {
                return null;
            }
            return users.get(0);
        }
        finally {
            db.close();
        }
    }

    @Override
    public void update(final IUser user) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded    
                .newConfiguration(), DB4OFILENAME);
        try {
            List<User> users = db.query(new Predicate<User>() {

                private static final long serialVersionUID = 1L;

                public boolean match(User userDb) {
                    return user.getName().equals(userDb.getName());
                }
            });
            IUser userDb = users.get(0);
            userDb.setPlayingField(user.getPlayingField());
            db.store(userDb);
        }
        finally {
            db.close();
        }
    }

    @Override
    public void delete() {
        throw new NotImplementedException("Delete method is not implemented yet");
    }

    @Override
    public boolean contains(final IUser user) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded    
                .newConfiguration(), DB4OFILENAME);
        
        try {
            List<User> users = db.query(new Predicate<User>() {

                private static final long serialVersionUID = 1L;

                public boolean match(User anotherUser) {
                    return anotherUser.getName().equals(user.getName());
                }
            });
            return !(users.size() == 0);
        }
        finally {
             db.close();
        }
    }
}
