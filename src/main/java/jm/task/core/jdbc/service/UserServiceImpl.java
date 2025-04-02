package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
  private UserDao userDao = new UserDaoHibernateImpl();
  private ServiceLogs logs = new ServiceLogs();

  public void createUsersTable() {
    userDao.createUsersTable();
    logs.logInfo("Таблица создана");
  }

  public void dropUsersTable() {
    userDao.dropUsersTable();
    logs.logInfo("Таблицца удалена");
  }

  public void saveUser(String name, String lastName, byte age) {
    userDao.saveUser(name, lastName, age);
    logs.logInfo("Юзер " + name + " " + lastName + " сохранён" );
  }

  public void removeUserById(long id) {
    userDao.removeUserById(id);
    logs.logInfo("Юзерс с ID:" + id +" удалён");
  }

  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  public void cleanUsersTable() {
    userDao.cleanUsersTable();
    logs.logInfo("Таблица очищена");
  }
}
