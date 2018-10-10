package ru.javawebinar.topjava.dao.interfaces;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Storage <T> {
    void create(T entity);
    T read(T entity);
    void update(T entity);
    void remove(AtomicInteger id);
    T getById(AtomicInteger id);
    AtomicInteger counterId();
    List<T> getAll();
}
