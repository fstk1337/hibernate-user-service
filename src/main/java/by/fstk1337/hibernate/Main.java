package by.fstk1337.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sf = new Configuration().buildSessionFactory();
        sf.close();
    }
}
