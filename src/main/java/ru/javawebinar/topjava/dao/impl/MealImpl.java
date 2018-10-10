package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.interfaces.MealStorage;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealImpl implements MealStorage {
    private static List<Meal> meals;

    static {
        meals = new CopyOnWriteArrayList<>();
    }

    @Override
    public void create(Meal meal) {
        meal.setId(counterId());
        meals.add(meal);
    }

    @Override
    public Meal read(Meal meal) {
        return meals.parallelStream()
                .filter(meal::equals)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Meal meal) throws NullPointerException {
        meals.remove(getById(meal.getId()));
        meals.add(meal);
    }

    @Override
    public void remove(AtomicInteger id) {
        for (Meal meal: meals) {
            if (meal.getId().get() == id.get())
                meals.remove(meal);
        }
    }

    @Override
    public Meal getById(AtomicInteger id) {
        return meals.parallelStream()
                .filter(meal -> meal.getId().get() == id.get())
                .findFirst()
                .orElse(null);
    }

    @Override
    public AtomicInteger counterId() {
        AtomicInteger id = new AtomicInteger(meals.size());
        id.incrementAndGet();
        return id;
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }
}
