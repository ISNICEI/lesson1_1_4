package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
  public UserDaoHibernateImpl() {

  }

  @Override
  public void createUsersTable() {
    Transaction tx = null;
    try (Session session = Util.HibernateGetConnection().openSession()) {
      tx = session.beginTransaction();
      String sql =
          """
                    CREATE TABLE IF NOT EXISTS  Users (
                        id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        lastName VARCHAR(255),
                        age TINYINT);""";
      session.createSQLQuery(sql).executeUpdate();
      tx.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      if (tx != null) {
        tx.rollback();
      }
    }
  }

  @Override
  public void dropUsersTable() {
    Transaction tx = null;
    try (Session session = Util.HibernateGetConnection().openSession()) {
      tx = session.beginTransaction();
      session.createSQLQuery("DROP TABLE users").executeUpdate();
      tx.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      if (tx != null) {
        tx.rollback();
      }
    }
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    Transaction tx = null;
    try (Session session = Util.HibernateGetConnection().openSession()) {
      tx = session.beginTransaction();
      User user = new User(name, lastName, age);
      session.save(user);
      tx.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      if (tx != null) {
        tx.rollback();
      }
    }
  }

  @Override
  public void removeUserById(long id) {
    Transaction tx = null;
    try (Session session = Util.HibernateGetConnection().openSession()) {
      tx = session.beginTransaction();
      User user = session.get(User.class, id);
      session.delete(user);
      tx.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      if (tx != null) {
        tx.rollback();
      }
    }
  }

  @Override
  public List<User> getAllUsers() {
    Transaction tx = null;
    List<User> users = new ArrayList<>();
    try (Session session = Util.HibernateGetConnection().openSession()) {
      tx = session.beginTransaction();
      users = session.createQuery("from User", User.class).list();
      tx.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      if (tx != null) {
        tx.rollback();
      }
    }
    return users;
  }

  @Override
  public void cleanUsersTable() {
    Transaction tx = null;
    try (Session session = Util.HibernateGetConnection().openSession()) {
      tx = session.beginTransaction();
      session.createQuery("delete from User").executeUpdate();
      tx.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
      if (tx != null) {
        tx.rollback();
      }
    }
  }
}
