package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {

}
