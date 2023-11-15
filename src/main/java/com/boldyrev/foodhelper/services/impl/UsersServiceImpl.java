package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.models.User;
import com.boldyrev.foodhelper.repositories.UsersRepository;
import com.boldyrev.foodhelper.security.Role;
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

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int id) {
        return usersRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("User with id '%d' not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return usersRepository.findByUsernameIgnoreCase(username).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("User with username='%s' not found", username)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> users = usersRepository.findAll();

        if (users.isEmpty()) {
            log.debug("Users not found");
            throw new EmptyDataException("Users not found");
        }

        return users;
    }

    @Override
    @Transactional
    public User signUp(User user) {
        return usersRepository.save(enrich(user));
    }

    @Override
    @Transactional
    public User update(int id, User user) {
        //todo user update
        return user;
    }

    @Override
    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    private User enrich(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        return user;
    }

}
