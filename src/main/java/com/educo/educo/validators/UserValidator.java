package com.educo.educo.validators;

import com.educo.educo.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errors.rejectValue("password", "Length", "Password must contain at least 6 characters.");
        }
        if (user.getConfirmPassword() == null || user.getPassword() == null || !user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Passwords do not match.");
        }
    }
}
