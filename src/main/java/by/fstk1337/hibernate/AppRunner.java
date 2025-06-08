package by.fstk1337.hibernate;

import by.fstk1337.hibernate.dao.UserDao;
import by.fstk1337.hibernate.entity.User;
import by.fstk1337.hibernate.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class AppRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        try (SessionFactory sf = configuration.buildSessionFactory();
             Session session = sf.openSession();
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            UserService us = new UserService(new UserDao(session));
            System.out.println("Welcome to User Service CLI. Please choose an option to proceed:");
            int option;
            while(true) {
                try {
                    us.printMenu();
                    option = Integer.parseInt(br.readLine());
                    if (option == 0) {
                        us.exit();
                        break;
                    }
                    switch (option) {
                        case 1:
                            System.out.println("Creating new user.");
                            System.out.println("Please enter user name:");
                            String name1 = br.readLine();
                            System.out.println("Please enter user email:");
                            String email1 = br.readLine();
                            System.out.println("Please enter user age:");
                            Integer age1 = Integer.parseInt(br.readLine());
                            User user1 = new User(name1, email1, age1);
                            user1 = us.createUser(user1);
                            System.out.println("New user created:");
                            System.out.println(user1);
                            break;
                        case 2:
                            System.out.println("Finding a user by ID.");
                            System.out.println("Please enter user ID:");
                            Long id1 = Long.parseLong(br.readLine());
                            User user2 = us.getUserById(id1);
                            if (Objects.nonNull(user2.getId())) {
                                System.out.println(user2);
                            }
                            break;
                        case 3:
                            System.out.println("Finding all users.");
                            List<User> users = us.getAllUsers();
                            System.out.println(users.size() + " users found.");
                            if (!users.isEmpty()) {
                                users.forEach(System.out::println);
                            }
                            break;
                        case 4:
                            System.out.println("Updating a user.");
                            System.out.println("Please enter user ID:");
                            Long id2 = Long.parseLong(br.readLine());
                            System.out.println("Please enter user name:");
                            String name2 = br.readLine();
                            System.out.println("Please enter user email:");
                            String email2 = br.readLine();
                            System.out.println("Please enter user age:");
                            Integer age2 = Integer.parseInt(br.readLine());
                            User user3 = new User(name2, email2, age2);
                            user3.setId(id2);
                            id2 = us.updateUser(user3);
                            System.out.println("User with ID=" + id2 + " was updated.");
                            break;
                        case 5:
                            System.out.println("Deleting a user.");
                            System.out.println("Please enter user ID:");
                            Long id3 = Long.parseLong(br.readLine());
                            id3 = us.deleteUser(id3);
                            System.out.println("User with ID=" + id3 + " was deleted.");
                            break;
                        default:
                            System.out.println("Invalid option, please try again.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Illegal input, please try again.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
