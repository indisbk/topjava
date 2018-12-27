package ru.javawebinar.topjava.web.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;

@Component
public class DateTimeValidator implements Validator {

    private final MealService service;
    private final MessageSource messageSource;

    @Autowired
    public DateTimeValidator(MealService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LocalDateTime dateTime;
        if (target instanceof Meal) {
            Meal meal = (Meal) target;
            dateTime = meal.getDateTime();
        } else {
            MealTo meal = (MealTo) target;
            dateTime = meal.getDateTime();
        }

        if(service.dateTimeIsExists(dateTime, SecurityUtil.authUserId())) {
            errors.rejectValue("dateTime", "", messageSource.getMessage("meal.dateTimeDuplication", null, LocaleContextHolder.getLocale()));
        }
    }
}
