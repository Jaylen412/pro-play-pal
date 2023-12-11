package com.gamestats.proplaypalrest.repo;

import com.gamestats.proplaypalrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    User findByUserName(String userName);
}
