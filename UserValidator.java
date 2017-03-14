package ctg.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ctg.model.Users;
import ctg.service.UserService;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        if (user.getUserName().length() < 6 || user.getUserName().length() > 32) {
            errors.rejectValue("userName", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "NotEmpty");
        if (user.getUserPassword().length() < 8 || user.getUserPassword().length() > 32) {
            errors.rejectValue("userPassword", "Size.userForm.password");
        }

        if (!user.getUserConfirmPassword().equals(user.getUserPassword())) {
            errors.rejectValue("userConfirmPassword", "Diff.userForm.passwordConfirm");
        }
    }
}
