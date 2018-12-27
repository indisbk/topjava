package ru.javawebinar.topjava.web.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class EmailValidator implements Validator {

    private final UserService service;
    private final MessageSource messageSource;

    @Autowired
    public EmailValidator(UserService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String email;
        if (target instanceof UserTo) {
            UserTo user = (UserTo) target;
            email = user.getEmail();
        } else {
            User user = (User) target;
            email = user.getEmail();
        }
        if(service.emailIsExists(email)) {
            errors.rejectValue("email", "", messageSource.getMessage("validator.emailDuplication", null, LocaleContextHolder.getLocale()));
        }
    }
}
