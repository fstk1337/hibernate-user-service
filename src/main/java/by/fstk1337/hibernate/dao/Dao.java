package by.fstk1337.hibernate.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> findOneById(Long id);
    List<T> findAll();
    T save(T t);
    List<T> saveAll(T[] ts);
    void update(T t);
    void delete(T t);
}
