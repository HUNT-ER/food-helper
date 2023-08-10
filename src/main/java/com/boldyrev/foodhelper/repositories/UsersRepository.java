package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameIgnoreCase(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.recipes WHERE u.id = :id")
    Optional<User> findById(@Param("id") int id);
}
