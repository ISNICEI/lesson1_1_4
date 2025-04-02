package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.ServiceLogs;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
  private static Connection connection = Util.getConnectionJDBC();
  private ServiceLogs logs;

  public UserDaoJDBCImpl() {
    try {
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      logs.logError("Автокомит не отключен", e);
    }
  }

  @Override
  public void createUsersTable() {
    String sql =
        "CREATE TABLE IF NOT EXISTS users ("
            + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
            + "name VARCHAR(45), "
            + "lastName VARCHAR(45), "
            + "age TINYINT)";
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
      connection.commit();
    } catch (SQLException e) {
      logs.logError("Проблемы с созданием таблицы", e);
    }
  }

  @Override
  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
      connection.commit();
    } catch (SQLException e) {
      logs.logError("Проблемы с удалением таблицы", e);
    }
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, lastName);
      preparedStatement.setByte(3, age);
      preparedStatement.executeUpdate();
      connection.commit();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ep) {
        logs.logError("Проблемы rollback", ep);
      }
      logs.logError("Проблемы с сохраниением", e);
    }
  }

  @Override
  public void removeUserById(long id) {
    String sql = "DELETE FROM users WHERE id = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
      connection.commit();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ep) {
        logs.logError("Проблемы rollback", ep);
      }
      logs.logError("Не смог удалить по ID", e);
    }
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM users";
    try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {
      while (resultSet.next()) {
        User user =
            new User(
                resultSet.getString("name"),
                resultSet.getString("lastName"),
                resultSet.getByte("age"));
        user.setId(resultSet.getLong("id"));
        users.add(user);
      }
      connection.commit();
    } catch (SQLException e) {
      logs.logError("Не смог получить всех Users", e);
    }
    return users;
  }

  @Override
  public void cleanUsersTable() {
    String sql = "TRUNCATE TABLE users";
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
      connection.commit();
    } catch (SQLException e) {
      logs.logError("Проблемы с удалением всех Userbase", e);
    }
  }
}
