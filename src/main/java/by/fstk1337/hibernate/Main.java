package by.fstk1337.hibernate;

import by.fstk1337.hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        try (SessionFactory sf = configuration.buildSessionFactory();
             Session session = sf.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User", User.class);
            List<User> users = query.list();
            users.forEach(System.out::println);
            session.getTransaction().commit();
        }
    }
}
