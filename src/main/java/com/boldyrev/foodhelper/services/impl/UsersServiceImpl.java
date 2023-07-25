package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.UserNotFoundException;
import com.boldyrev.foodhelper.models.User;
import com.boldyrev.foodhelper.repositories.UsersRepository;
import com.boldyrev.foodhelper.services.UsersService;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        log.debug("Getting user with username '{}'", username);
        return usersRepository.findByUsernameIgnoreCase(username).orElseThrow(
            () -> new UserNotFoundException(
                String.format("User with username '%s' not found", username)));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int id) {
        log.debug("Getting user with id={}", id);
        return usersRepository.findById(id).orElseThrow(
            () -> new UserNotFoundException(String.format("User with id '%d' not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        log.debug("Getting all users");
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public User register(User user) {
        log.info("Register new user with username '{}'", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return usersRepository.save(user);
    }


    @Override
    @Transactional
    public void update(int id, User user) {
        log.debug("Updating user with id={}", id);

        User storedUser = this.findById(id);
        storedUser.setRecipes(user.getRecipes());
        storedUser.setUsername(user.getUsername());
        storedUser.setRole(user.getRole());

        if (!storedUser.getPassword().equals(user.getPassword())) {
            log.debug("Changing password by user id ={}", id);
            storedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        usersRepository.save(storedUser);
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Deleting user with id={}", id);
        usersRepository.deleteById(id);
    }

}
