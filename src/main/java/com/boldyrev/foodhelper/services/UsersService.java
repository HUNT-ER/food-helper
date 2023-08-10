package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.User;
import java.util.List;
import java.util.Optional;

public interface UsersService {

    User findById(int id);
    User findByUsername(String username);
    List<User> findAll();
    User signUp(User user);
    User update(int id, User user);
    void delete(int id);
}
