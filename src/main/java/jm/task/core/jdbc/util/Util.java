package jm.task.core.jdbc.util;
import java.sql.*;

public class Util {
  private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "162160Zz";
  private static Connection connection;

  public static Connection getConnection() {
    try {
      return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
