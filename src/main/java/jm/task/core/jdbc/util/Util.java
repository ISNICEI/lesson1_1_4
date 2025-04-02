package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateError;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.*;
import java.util.Properties;

public class Util {
  private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "162160Zz";
  private static Connection connection;
  private static SessionFactory sessionFactory;

  private Util() {}

  public static SessionFactory HibernateGetConnection() {

    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, DB_URL + "?useSSL=false");
        settings.put(Environment.USER, DB_USER);
        settings.put(Environment.PASS, DB_PASSWORD);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        settings.put(Environment.SHOW_SQL, "true");

        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        settings.put(Environment.HBM2DDL_AUTO, "create-drop");

        configuration.setProperties(settings);

        configuration.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry =
            new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
      } catch (HibernateError e) {
        System.out.println("Error Hibernate connection");
      }
    }
    return sessionFactory;
  }

  public static void FactoryClose() {
    sessionFactory.close();
  }

  public static Connection getConnectionJDBC() {
    try {
      return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void closeConnectionJDBC(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
