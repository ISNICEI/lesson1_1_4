package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {
    UserService userService = new UserServiceImpl();
    ArrayList<User> UsersList = new ArrayList<>();
    User user1 = new User("Ivan", "Ivanov", (byte) 19);
    User user2 = new User("Petr", "Petrov", (byte) 20);
    User user3 = new User("Semen", "Semenov", (byte) 22);
    User user4 = new User("Sidor", "Sidorov", (byte) 25);
    UsersList.add(user1);
    UsersList.add(user2);
    UsersList.add(user3);
    UsersList.add(user4);
    userService.createUsersTable();

    for (User u : UsersList) {
      userService.saveUser(u.getName(), u.getLastName(), u.getAge());
      System.out.println("Пользователь с именем : " + u.getName() + " добавлен в таблицу");
    }
    userService.getAllUsers();
    userService.cleanUsersTable();
    userService.dropUsersTable();
  }
}
