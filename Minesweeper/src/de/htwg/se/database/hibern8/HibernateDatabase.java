package de.htwg.se.database.hibern8;

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
import java.util.List;

public class HibernateDatabase implements DataAccessObject{
    private static final int BORDER = 2;

    private IUser getUserData(HibernateUser hibernateUser, Session session) {
        String queryStr;
        Query query;
        List<HibernateStatistic> statResult;
        List<HibernateField> fieldResult;
        List<HibernateCell> cellResult;
        HibernateStatistic hibernateStatistic;
        HibernateField hibernateField;
        IStatistic statistic;
        IField field;

        // Users data
        IUser user = new User();
        user.setId(hibernateUser.getUserid());
        user.setSalt(hibernateUser.getSalt());
        user.setEncryptedPassword(hibernateUser.getEncryptedPassword());
        user.setAlgorithm(hibernateUser.getAlgorithm());
        user.setName(hibernateUser.getName());

        // Statistics data
        queryStr = "FROM HibernateStatistic stat WHERE stat.statid = :statid";
        query = session.createQuery(queryStr);
        query.setParameter("statid", hibernateUser.getStatid());
        statResult = query.list();
        if (statResult.isEmpty()) {
            statistic = new Statistic();
        } else {
            hibernateStatistic = statResult.get(0);
            statistic = new Statistic(
                    hibernateUser.getStatid(),
                    hibernateStatistic.getGamesPlayed(),
                    hibernateStatistic.getGamesWon(),
                    hibernateStatistic.getTimeSpent(),
                    hibernateStatistic.getMinTime()
            );
        }
        user.setStatistic(statistic);

        // Playing fields data
        queryStr = "FROM HibernateField field WHERE field.fieldid = :fieldid";
        query = session.createQuery(queryStr);
        query.setParameter("fieldid", hibernateUser.getFieldid());
        fieldResult = query.list();
        int lines, columns;
        if (fieldResult.isEmpty()) {
            field = new Field();
            lines = field.getLines() + BORDER;
            columns = field.getColumns() + BORDER;
        } else {
            hibernateField = fieldResult.get(0);
            lines = hibernateField.getLines() + BORDER;
            columns = hibernateField.getColumns() + BORDER;
            field = new Field(
                    hibernateUser.getFieldid(),
                    lines - BORDER,
                    columns - BORDER,
                    hibernateField.getnMines()
            );
        }

        // cell data
        ICell[][] cells = new ICell[lines][columns];
        queryStr = "FROM HibernateCell cell WHERE cell.fieldid = :fieldid";
        System.out.println("SELEKTIRUEM");
        query = session.createQuery(queryStr);
        query.setParameter("fieldid", hibernateUser.getFieldid());
        cellResult = query.list();
        if (!cellResult.isEmpty()) {
            int index; // to get the right place of the cell in matrix
            for (HibernateCell hibernateCell: cellResult) {
                index = hibernateCell.getIndex();
                cells[(index / 100)][(index % 100)] = new Cell(
                        hibernateCell.getCellid(),
                        hibernateCell.getValue(),
                        hibernateCell.getIsRevealed() == 1 ? Boolean.TRUE : Boolean.FALSE,
                        hibernateUser.getFieldid()
                );
            }
            field.setPlayingField(cells);
        }

        user.setPlayingField(field);
        return user;
    }

    private HibernateUser saveUserData(IUser user, Session session) {
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
        HibernateStatistic hibernateStatistic = new HibernateStatistic(
                statistic.getId(),
                statistic.getGamesWon(),
                statistic.getTimeSpent(),
                statistic.getMinTime(),
                statistic.getGamesPlayed(),
                hibernateUser
        );
        session.saveOrUpdate(hibernateStatistic);
        hibernateUser.setStatistic(hibernateStatistic);
        hibernateUser.setStatid(statistic.getId());
        // Playing fields data
        IField field = user.getPlayingField();
        int lines = field.getLines();
        int columns = field.getColumns();
        HibernateField hibernateField = new HibernateField(
                field.getFieldID(),
                field.getnMines(),
                lines,
                columns,
                hibernateUser
        );
        session.saveOrUpdate(hibernateField);
        hibernateUser.setField(hibernateField);
        hibernateUser.setFieldid(field.getFieldID());
        session.saveOrUpdate(hibernateUser);

        ICell cell;
        HibernateCell hCell;
        // Cell data
        for (int i = 0; i < lines + BORDER; i++) {
            for (int j = 0; j < columns + BORDER; j++) {
                cell = field.getPlayingField()[i][j];
                hCell = new HibernateCell(
                        cell.getId(),
                        cell.getValue(),
                        cell.getIsRevealed() ? 1 : 0,
                        i * 100 + j,
                        hibernateField.getFieldid(),
                        hibernateField
                );
                hibernateField.getCells().add(hCell);
                session.saveOrUpdate(hCell);
            }
        }
        session.saveOrUpdate(hibernateField);
        return hibernateUser;
    }

    private void deleteUserData(IUser user, Session session) {
        if (user == null) {
            return;
        }
        // get user entry
        String queryStr = "FROM HibernateUser usr WHERE usr.name = :username";
        Query query = session.createQuery(queryStr);
        query.setParameter("username", user.getName());
        List<HibernateUser> userResult = query.list();
        HibernateUser hibernateUser = userResult.get(0);
        if (userResult.isEmpty()) {
            return;
        }
        // get & delete statistic entry
        queryStr = "FROM HibernateStatistic stat WHERE stat.statid = :statid";
        query = session.createQuery(queryStr);
        query.setParameter("statid", user.getStatistic().getId());
        List<HibernateStatistic> statResult = query.list();
        if (!statResult.isEmpty()) {
            session.delete(statResult.get(0));
        }
        // get & delete cell entries
        queryStr = "FROM HibernateCell cell WHERE cell.fieldid = :fieldid";
        query = session.createQuery(queryStr);
        query.setParameter("fieldid", hibernateUser.getFieldid());
        List<HibernateCell> cellResult = query.list();
        if (!cellResult.isEmpty()) {
            for(HibernateCell cell: cellResult) {
                session.delete(cell);
            }
        }
        // get & delete field entry
        queryStr = "FROM HibernateField field WHERE field.fieldid = :fieldid";
        query = session.createQuery(queryStr);
        query.setParameter("fieldid", hibernateUser.getFieldid());
        List<HibernateField> fieldResult = query.list();
        if (!fieldResult.isEmpty()) {
            session.delete(fieldResult.get(0));
        }
        // after all delete the user
        session.delete(hibernateUser);
    }

    public void create(IUser user) {
        this.update(user);
    }

    public IUser read(String username) {
        Transaction tx = null;
        Session session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            String queryStr = "FROM HibernateUser usr WHERE usr.name = :username";
            Query query = session.createQuery(queryStr);
            query.setParameter("username", username);
            List<HibernateUser> result = query.list();
            if (result.isEmpty()) {
                return null;
            }
            return this.getUserData(result.get(0), session);
        } catch (HibernateException e) {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void update(IUser user) {
        Transaction tx = null;
        Session session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            this.saveUserData(user, session);
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
            this.deleteUserData(user, session);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            throw new RuntimeException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    public boolean contains(IUser user) {
        if (user == null) {
            return false;
        }
        Transaction tx = null;
        Session session = HibernateUtil.getSession();
        try {
            tx = session.beginTransaction();
            String queryStr = "FROM HibernateUser usr WHERE usr.name = :username";
            Query query = session.createQuery(queryStr);
            query.setParameter("username", user.getName());
            List<HibernateUser> result = query.list();
            return !result.isEmpty();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
