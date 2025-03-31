package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
  public UserDaoJDBCImpl() {}

  public void createUsersTable() {
    String sql =
        "CREATE TABLE IF NOT EXISTS users ("
            + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
            + "name VARCHAR(45), "
            + "lastName VARCHAR(45), "
            + "age TINYINT)";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveUser(String name, String lastName, byte age) {
    String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, lastName);
      preparedStatement.setByte(3, age);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void removeUserById(long id) {
    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM users";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
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
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  public void cleanUsersTable() {
    String sql = "TRUNCATE TABLE users";
    try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
