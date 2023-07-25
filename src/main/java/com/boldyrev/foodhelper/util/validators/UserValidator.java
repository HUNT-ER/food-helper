package com.boldyrev.foodhelper.util.validators;

import com.boldyrev.foodhelper.models.User;
import com.boldyrev.foodhelper.repositories.UsersRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UsersRepository usersRepository;

    @Autowired
    public UserValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> storedUser = usersRepository.findByUsernameIgnoreCase(user.getUsername());

        if (storedUser.isPresent()) {
            errors.rejectValue("username", "username_exists_error", "This username already taken");
        }
    }
}
