package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles({"datajpa", "common-of-data-jpa"})
public class UserServiceDataTest extends AbstractUserServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private UserService userService;

    @Test
    public void getUserAndItsMeals() {
        User user = userService.get(USER_ID);
        List<Meal> meals = mealService.getAllByUser(user);

        assertMatch(user, USER);
        assertMatch(meals, MEALS);
    }

}