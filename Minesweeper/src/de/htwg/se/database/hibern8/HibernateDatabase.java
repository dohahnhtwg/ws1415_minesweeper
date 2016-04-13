package de.htwg.se.database.hibern8;

import com.db4o.foundation.NotImplementedException;
import de.htwg.se.database.DataAccessObject;
import de.htwg.se.model.ICell;
import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import de.htwg.se.model.impl.Cell;
import de.htwg.se.model.impl.Field;
import de.htwg.se.model.impl.User;
import de.htwg.se.model.impl.Statistic;
import org.hibernate.*;

import java.util.LinkedList;
import java.util.List;

public class HibernateDatabase implements DataAccessObject{

    private IUser copyUserData(HibernateUser hibernateUser) {
        if (hibernateUser == null) {
            return null;
        }
        // Users data
        IUser user = new User(hibernateUser.getName(), "dummy");
        user.setId(hibernateUser.getUserid());
        user.setSalt(hibernateUser.getSalt());
        user.setEncryptedPassword(hibernateUser.getEncryptedPassword());
        user.setAlgorithm(hibernateUser.getAlgorithm());
        // Statistics data
        IStatistic statistic = new Statistic();
        HibernateStatistic hibernateStatistic = hibernateUser.getStatistic();
        statistic.setId(hibernateStatistic.getStatid());
        statistic.setGamesPlayed(hibernateStatistic.getGamesPlayed());
        statistic.setGamesWon(hibernateStatistic.getGamesWon());
        statistic.setTimeSpent(hibernateStatistic.getTimeSpent());
        statistic.setMinTime(hibernateStatistic.getMinTime());
        user.setStatistic(statistic);
        // Playing fields data
        HibernateField hibern8Field = hibernateUser.getField();
        int lines = hibern8Field.getLines();
        int columns = hibern8Field.getColumns();
        IField field = new Field(lines, columns, hibern8Field.getnMines());
        field.setFieldID(hibern8Field.getFieldid());
        ICell[][] cells = new ICell[lines][columns];
        HibernateCell hcell;
        int ind = 0;
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                hcell = hibern8Field.getPlayingField().get(ind);
                cells[i][j] = new Cell(hcell.getValue());
                cells[i][j].setId(hcell.getId());
                cells[i][j].setIsRevealed(hcell.getIsRevealed());
                ind++;
            }
        }
        field.setPlayingField(cells);
        user.setPlayingField(field);
        return user;
    }

    private HibernateUser copyUserData(IUser user) {
        if (user == null) {
            return null;
        }
        // Users data
        HibernateUser hibernateUser = new HibernateUser();
        hibernateUser.setUserid(user.getId());
        hibernateUser.setAlgorithm(user.getAlgorithm());
        hibernateUser.setEncryptedPassword(user.getEncryptedPassword());
        hibernateUser.setName(user.getName());
        hibernateUser.setSalt(user.getSalt());
        // Statistics data
        IStatistic statistic = user.getStatistic();
        HibernateStatistic hibernateStatistic = new HibernateStatistic();
        hibernateStatistic.setStatid(statistic.getId());
        hibernateStatistic.setGamesWon(statistic.getGamesWon());
        hibernateStatistic.setMinTime(statistic.getMinTime());
        hibernateStatistic.setTimeSpent(statistic.getTimeSpent());
        hibernateUser.setStatistic(hibernateStatistic);
        // Playing fields data
        IField field = user.getPlayingField();
        int lines = field.getLines();
        int columns = field.getColumns();
        ICell cell;
        HibernateCell hcell;
        HibernateField hibernateField = new HibernateField();
        LinkedList<HibernateCell> hcells = new LinkedList<>();
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                cell = field.getPlayingField()[i][j];
                hcell = new HibernateCell(cell.getValue());
                hcell.setId(cell.getId());
                hcell.seIstRevealed(cell.getIsRevealed());
                hcells.add(hcell);
            }
        }
        hibernateField.setPlayingField(hcells);
        hibernateUser.setField(hibernateField);
        return hibernateUser;
    }

    @Override
    public void create(IUser user) {
        Transaction tx = null;
        Session session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            HibernateUser hibernateUser = this.copyUserData(user);
            if (hibernateUser != null) {
                session.save(hibernateUser);
            }
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            throw new RuntimeException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public IUser read(String username) {
        Transaction tx = null;
        Session session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            String queryStr = "FROM HibernateUser usr WHERE usr.name = :username";
            Query query = session.createQuery(queryStr);
            query.setParameter("username", username);
            List result = query.list();
            // TODO: fetch HibernateUser from results
//            HibernateUser hibernateUser = result.get(0);
//            for (Object aResult : result) {
//                return (IUser) this.copyUserData(aResult);
//            }
        } catch (HibernateException e) {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void update(IUser user) {
        Transaction tx = null;
        Session session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            HibernateUser hibernateUser = this.copyUserData(user);
            session.saveOrUpdate(hibernateUser);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            throw new RuntimeException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void delete(IUser user) {
        Transaction tx = null;
        Session session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            HibernateUser hibernateUser = this.copyUserData(user);
            session.delete(hibernateUser);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            throw new RuntimeException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public boolean contains(IUser user) {
        IUser usr = this.read(user.getName());
        return usr != null;
    }
}
