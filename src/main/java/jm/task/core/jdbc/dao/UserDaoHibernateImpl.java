package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.ServiceLogs;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
  private ServiceLogs logs;
  private Transaction tx = null;
  public UserDaoHibernateImpl() {
    this.logs = new ServiceLogs();
  }
  @FunctionalInterface
  interface SessionConsumer {
    void accept(Session session);
  }
  private void executeTransaction(SessionConsumer cons) {
    try (Session session = Util.hibernateGetConnection().openSession()) {
      tx = session.beginTransaction();
      cons.accept(session);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) {
        tx.rollback();
      }
      logs.logError("Транзакция не удалась", e);
    }
  }

  @Override
  public void createUsersTable() {
      String sql =
          "CREATE TABLE IF NOT EXISTS users ("
              + "id INT AUTO_INCREMENT PRIMARY KEY, "
              + "name VARCHAR(50), "
              + "lastName VARCHAR(50), age INT)";
      executeTransaction(session ->  session.createSQLQuery(sql).executeUpdate());

  }

  @Override
  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";
    executeTransaction(session -> session.createSQLQuery(sql).executeUpdate());
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    try (Session session = Util.hibernateGetConnection().openSession()) {
      User user = new User(name, lastName, age);
      session.save(user);
    } catch (HibernateException e) {
      logs.logError("Юзер не сохранён",e);
    }
  }

  @Override
  public void removeUserById(long id) {
    try (Session session = Util.hibernateGetConnection().openSession()) {
      User user = session.get(User.class, id);
      if (user != null) {
        session.delete(user);
        logs.logInfo("Пользователь с ID:" + id + " успешно удалён.");
      }else {
        logs.logInfo("Пользователь с ID:" + id + " не найден.");
      }
    } catch (HibernateException e) {
      logs.logError("Юзер с ID:" + id +" не удалён",e);
    }
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try (Session session = Util.hibernateGetConnection().openSession()) {
      users = session.createQuery("from User", User.class).list();
    } catch (HibernateException e) {
      logs.logError("Не удалось получить список юзеров",e);
    }
    return users;
  }

  @Override
  public void cleanUsersTable() {
    executeTransaction(session -> session.createQuery("delete from User").executeUpdate());
  }
}
