package by.fstk1337.hibernate.service;

import by.fstk1337.hibernate.dao.UserDao;
import by.fstk1337.hibernate.entity.User;

import java.util.List;

public class UserService {
    private static final User EMPTY_USER = new User("", "", 0);
    private final UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void printMenu() {
        System.out.println("1. Create new user;");
        System.out.println("2. Find user by ID;");
        System.out.println("3. Find all users;");
        System.out.println("4. Update user;");
        System.out.println("5. Delete user;");
        System.out.println("0. Exit application");
    }

    public User createUser(User user) {
        return userDao.save(user);
    }

    public User getUserById(Long id) {
        return userDao.findOneById(id).orElse(EMPTY_USER);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public Long updateUser(User user) {
        return userDao.update(user);
    }

    public Long deleteUser(Long id) {
        User user = userDao.findOneById(id).orElse(EMPTY_USER);
        return userDao.delete(user);
    }

    public void exit() {
        System.out.println("Exiting...");
    }
}
