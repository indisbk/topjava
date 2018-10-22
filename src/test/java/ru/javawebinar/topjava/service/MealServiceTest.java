package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {

        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL5_ID, ADMIN_ID);
        assertMatch(meal, MEAL1_ADMIN);
    }

    @Test
    public void delete() {
        service.delete(MEAL5_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), MEAL2_ADMIN);
    }

    @Test
    public void getBetweenDates() throws Exception{
        List<Meal> allBetween = service.getBetweenDates(startDateTime.toLocalDate(), endDateTime.toLocalDate(), USER_ID);
        assertMatch(allBetween, MEAL3_USER, MEAL4_USER, MEAL1_USER, MEAL2_USER);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> allBetween = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        assertMatch(allBetween, MEAL3_USER, MEAL1_USER, MEAL2_USER);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL3_USER, MEAL4_USER, MEAL1_USER, MEAL2_USER);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1_USER);
        updated.setDescription("Завтрак");
        updated.setCalories(200);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void create() throws Exception{
        Meal newMealUser = new Meal(null, LocalDateTime.of(2018, 10, 22, 16, 00, 00),
                "Dinner", 600);
        Meal created = service.create(newMealUser, USER_ID);
        newMealUser.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMealUser, MEAL3_USER, MEAL4_USER, MEAL1_USER, MEAL2_USER);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(MEAL5_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL5_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MEAL1_ADMIN);
        updated.setDescription("Завтрак");
        updated.setCalories(200);
        service.update(updated, USER_ID);
    }
}