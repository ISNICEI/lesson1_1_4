package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    UserService userService = new UserServiceImpl();

    userService.createUsersTable();

    userService.saveUser("Alex", "Smith1", (byte) 25);
    userService.saveUser("John", "Smith2", (byte) 46);
    userService.saveUser("Fedor", "Smith3", (byte) 37);
    userService.saveUser("Igor", "Smith4", (byte) 18);

    List<User> usersList = userService.getAllUsers();
    usersList.forEach(System.out::println);

    userService.cleanUsersTable();

    userService.dropUsersTable();

    Util.FactoryClose();
  }
}
