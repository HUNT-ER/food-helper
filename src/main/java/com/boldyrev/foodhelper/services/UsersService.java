package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.User;
import java.util.List;
import java.util.Optional;

public interface UsersService {

    User findByUsername(String username);

    User findById(int id);

    List<User> findAll();

    User register(User user);

    void update(int id, User user);

    void delete(int id);



}
