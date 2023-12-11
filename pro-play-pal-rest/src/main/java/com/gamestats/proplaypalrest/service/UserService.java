package com.gamestats.proplaypalrest.service;

import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import com.gamestats.proplaypalrest.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class UserService {
    private UserRepo userRepo;
    private UserMapper userMapper;

    @Autowired
    public UserService( UserRepo userRepo,
                        UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }
    public User createUser(User user) {
        User newUser = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .createdDate(Instant.now())
                .build();
        log.info(String.format("Saving user: %s", user.getUserName()));
       return userRepo.save(newUser);
    }

    public UserDto getUser(UUID userId) {
      try {
          User user = userRepo.findById(userId).get();
          if (nonNull(user)) {
              UserDto userDto = userMapper.userEntityToDto(user);
              log.info(String.format("Returning user: %s", userId));
              return userDto;
          }
      } catch (Exception e) {
         log.error(e.getMessage());
      }
        throw new NoSuchElementException(String.format("User: [%s] was not able to be located", userId));
    }



}
