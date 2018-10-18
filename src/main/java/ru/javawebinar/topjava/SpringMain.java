package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) {
        // java 7 Automatic resource management

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            ProfileRestController profileRestController = appCtx.getBean(ProfileRestController.class);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.get(4);
        }
    }
}
