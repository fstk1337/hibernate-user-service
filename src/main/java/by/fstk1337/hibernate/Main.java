package by.fstk1337.hibernate;

import by.fstk1337.hibernate.dao.UserDao;
import by.fstk1337.hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        try (SessionFactory sf = configuration.buildSessionFactory();
             Session session = sf.openSession()) {
            UserDao userDao = new UserDao(session);
            User user1 = new User("Vitali", "vit.shvaichuk@yandex.by", 34);
            User user2 = new User("Misha", "misha@tut.by", 16);
            User user3 = new User("Grisha", "grishanya@mail.ru", 28);
            User user4 = new User("Masha", "masha@gmail.com", 22);
            List<User> users = userDao.saveAll(user1, user2, user3, user4);
            users.forEach(System.out::println);

            User user7 = new User("Grisha", "grishanya@mail.ru", 28);

            User user8 = new User("Masha", "masha@gmail.com", 22);

            userDao.delete(user7);
            userDao.delete(user8);

            User user5 = new User("Vitya", "vitya@yahoo.com", 25);
            userDao.update(user5);

            User user6 = new User("Peter", "masha@gmail.com", 33);
            userDao.update(user6);

            List<User> users1 = userDao.findAll();
            users1.forEach(System.out::println);
        }
    }
}
