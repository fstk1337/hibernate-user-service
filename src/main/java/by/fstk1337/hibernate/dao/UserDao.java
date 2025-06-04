package by.fstk1337.hibernate.dao;

import by.fstk1337.hibernate.entity.User;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDao implements Dao<User> {
    private final Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    private boolean emailExists(String email) {
        session.beginTransaction();
        Query<User> query = session.createQuery("from User where email='" + email + "'", User.class);
        User user = query.uniqueResult();
        session.getTransaction().commit();
        return Objects.nonNull(user);
    }

    private Optional<User> findByEmail(String email) {
        session.beginTransaction();
        Query<User> query = session.createQuery("from User where email='" + email + "'", User.class);
        Optional<User> user = query.uniqueResultOptional();
        session.getTransaction().commit();
        user.ifPresentOrElse(usr -> System.out.println("User was found by email."), () -> System.out.println("No user with such email found."));
        return user;
    }

    @Override
    public Optional<User> findOneById(Long id) {
        session.beginTransaction();
        Query<User> query = session.createQuery("from User where id=" + id, User.class);
        Optional<User> user = query.uniqueResultOptional();
        session.getTransaction().commit();
        user.ifPresentOrElse(usr -> System.out.println("User was fetched by id."), () -> System.out.println("No user found."));
        return user;
    }

    @Override
    public List<User> findAll() {
        session.beginTransaction();
        Query<User> query = session.createQuery("from User", User.class);
        List<User> users = query.list();
        session.getTransaction().commit();
        System.out.println(users.size() + " records were fetched.");
        return users;
    }

    @Override
    public User save(User user) {
        if (emailExists(user.getEmail())) {
            System.out.println("User with such email already exists, can't create user!");
            return null;
        }
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        System.out.println("The user was created.");
        return user;
    }

    @Override
    public List<User> saveAll(User ...users) {
        List<User> newUsers = new ArrayList<>();
        Arrays.stream(users).forEach(user -> {
            User newUser = this.save(user);
            if (Objects.nonNull(newUser)) {
                newUsers.add(newUser);
            }
        });
        System.out.println("In total: " + newUsers.size() + " users were created.");
        return newUsers;
    }

    @Override
    public void update(User user) {
        Optional<User> oldUser;
        if (Objects.isNull(user.getId())) {
            oldUser = this.findByEmail(user.getEmail());
        } else {
            oldUser = this.findOneById(user.getId());
        }
        if (Objects.isNull(oldUser.orElse(null))) {
            System.out.println("User not found, cannot update.");
            return;
        }
        if (oldUser.get().getEmail().equals(user.getEmail()) && !oldUser.get().getId().equals(user.getId())) {
            System.out.println("Cannot update user email, because such one already exists.");
        } else {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
            System.out.println("The user was updated.");
        }
    }

    @Override
    public void delete(User user) {
        Optional<User> oldUser;
        if (Objects.isNull(user.getId())) {
            oldUser = this.findByEmail(user.getEmail());
        } else {
            oldUser = this.findOneById(user.getId());
        }
        if (Objects.isNull(oldUser.orElse(null)) || !oldUser.get().equals(user)) {
            System.out.println("User not found, cannot delete.");
        } else {
            session.beginTransaction();
            session.remove(oldUser.get());
            session.getTransaction().commit();
            System.out.println("The user was removed.");
        }
    }
}
