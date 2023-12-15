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

import static java.util.Objects.isNull;
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
    public UserDto createUser(User user) throws Exception {
        if (isNull(userRepo.findByUserName(user.getUserName()))) {
            User newUser = User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userName(user.getUserName())
                    .password(user.getPassword())
                    .userRole(user.getUserRole())
                    .createdDate(Instant.now())
                    .build();
            log.info(String.format("Saving user: %s", user.getUserName()));
            userRepo.save(newUser);
            return userMapper.userEntityToDto(newUser);
        }
        throw new Exception(String.format("User [%s] Already Exist",user.getUserName()));
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

    // TODO: Refactor with a target user and source user in mind
    public UserDto updateUser(UserDto userDto) {
        Optional<User> user = userRepo.findById(userDto.getUserId());
        if (nonNull(user)) {
            User updatedUser = user.get();
            updatedUser.setUserId(userDto.getUserId());
//            check if username exist if it does check if it belongs to the current user if so allow it if not "username already exist"
//            updatedUser.setUserName();
            updatedUser.setFavoriteTeam(userDto.getFavoriteTeam());
//            Take updated password, pass into password hasher
//            updatedUser.setPassword();
            userRepo.save(updatedUser);
            return userMapper.userEntityToDto(updatedUser);
        }
        return null;
    }

//    TODO: Password hashing


}
