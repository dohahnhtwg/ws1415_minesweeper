package de.htwg.se.database.hibern8;

import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.IUser;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDatabase implements DataAccessObject{
    @Override
    public void create(IUser user) {

    }

    @Override
    public IUser read(String username, String password) {
        return null;
    }

    @Override
    public void update(IUser user) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            // TODO
//            HibernateUser user =
//            session.saveOrUpdate();


            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            throw new RuntimeException(ex.getMessage());

        }
    }

    @Override
    public void delete() {

    }

    @Override
    public boolean contains(IUser user) {
        return false;
    }
}
