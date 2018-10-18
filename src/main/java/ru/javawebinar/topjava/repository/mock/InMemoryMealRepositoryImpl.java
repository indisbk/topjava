package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500), 1);
        save(new Meal(LocalDateTime.of(2018, Month.MAY, 30, 13, 0), "Обед", 1000), 1);
        save(new Meal(LocalDateTime.of(2018, Month.MAY, 30, 20, 0), "Ужин", 500), 1);
        save(new Meal(LocalDateTime.of(2018, Month.MAY, 31, 10, 0), "Завтрак", 1000), 1);
        save(new Meal(LocalDateTime.of(2018, Month.MAY, 31, 13, 0), "Обед", 500),1);
        save(new Meal(LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "Ужин", 510),1);

        save(new Meal(LocalDateTime.of(2018, Month.JUNE, 1, 13, 0), "Обед", 500),2);
        save(new Meal(LocalDateTime.of(2018, Month.JUNE, 2, 20, 0), "Ужин", 510),2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            return get(meal.getId(), userId) == null ? null : repository.compute(meal.getId(), (id, oldValue) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if(get(id, userId) == null) {
            return false;
        } else {
            repository.remove(id);
            return true;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id) == null || repository.get(id).getUserId() != userId ? null : repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        if(repository.isEmpty()) {
            log.info("List of meals is empty");
            return Collections.emptyList();
        } else {
            return repository.values()
                    .stream()
                    .filter(meal -> meal.getUserId() == userId)
                    .sorted(Comparator.comparing(Meal::getDate).reversed())
                    .collect(Collectors.toList());
        }
    }
}

