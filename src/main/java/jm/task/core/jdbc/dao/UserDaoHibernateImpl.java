package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_NEW_TABLE = "CREATE TABLE IF NOT EXISTS user (\n" +
            "id int PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
            "name VARCHAR(45) NOT NULL,\n" +
            "lastName VARCHAR(45) NOT NULL,\n" +
            "age int NOT NULL\n" + ");";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS user";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getInstance().getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_NEW_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {

            }
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;

        try {
            session =  Util.getInstance().getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            transaction.commit();
        } catch ( Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {

            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;

        try {
            session =  Util.getInstance().getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {

            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;

        try {
            session =  Util.getInstance().getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch ( Exception e ) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch ( HibernateException e ) {

            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Session session = null;
        Transaction transaction = null;

        try {
            session =  Util.getInstance().getSessionFactory().openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;

        try {
            session =  Util.getInstance().getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_NEW_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
