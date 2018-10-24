package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final int MEAL3_ID = START_SEQ + 4;
    public static final int MEAL4_ID = START_SEQ + 5;
    public static final int MEAL5_ID = START_SEQ + 6;
    public static final int MEAL6_ID = START_SEQ + 7;

    public static final LocalDateTime startDateTime = LocalDateTime.of(2018, 10, 20, 7, 0, 0);
    public static final LocalDateTime endDateTime = LocalDateTime.of(2018, 10, 21, 7, 0, 0);

    public static final Meal MEAL1_USER = new Meal(MEAL1_ID, LocalDateTime.of(2018, 10, 20, 7, 0, 0), "Breakfast", 600);
    public static final Meal MEAL2_USER = new Meal(MEAL2_ID, LocalDateTime.of(2018, 10, 20, 12, 0, 0), "Lunch", 1000);
    public static final Meal MEAL3_USER = new Meal(MEAL3_ID, LocalDateTime.of(2018, 10, 21, 7, 0, 0), "Breakfast", 700);
    public static final Meal MEAL4_USER = new Meal(MEAL4_ID, LocalDateTime.of(2018, 10, 21, 12, 0, 0), "Lunch", 800);
    public static final Meal MEAL1_ADMIN = new Meal(MEAL5_ID, LocalDateTime.of(2018, 10, 21, 13, 30, 0), "Lunch", 1400);
    public static final Meal MEAL2_ADMIN = new Meal(MEAL6_ID, LocalDateTime.of(2018, 10, 21, 19, 10, 0), "Dinner", 300);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
