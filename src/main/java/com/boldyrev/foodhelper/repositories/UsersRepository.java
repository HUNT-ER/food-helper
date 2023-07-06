package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameIgnoreCase(String username);
}
