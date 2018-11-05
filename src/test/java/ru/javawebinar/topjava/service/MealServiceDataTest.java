package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"datajpa", "common-of-data-jpa"})
public class MealServiceDataTest extends AbstractMealServiceTest{
}