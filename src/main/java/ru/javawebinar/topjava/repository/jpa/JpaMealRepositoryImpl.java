package ru.javawebinar.topjava.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(JpaMealRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if(meal.isNew()) {
            em.persist(meal);
            return meal;
        } else if(get(meal.getId(), userId) != null) {
            return em.merge(meal);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        try {
            return em.createNamedQuery(Meal.DELETE)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .executeUpdate() != 0;
        } catch (EntityNotFoundException e){
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = null;
        try {
            meal = em.createNamedQuery(Meal.GET, Meal.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("It is not your meal ApplePieF*cker!!!");
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED_BY_DATETIME, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.BETWEEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}