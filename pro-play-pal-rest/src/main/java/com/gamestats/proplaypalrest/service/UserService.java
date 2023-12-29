package com.gamestats.proplaypalrest.service;

import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import com.gamestats.proplaypalrest.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

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
                    .password(hashUserPassword(user.getPassword()))
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

    public UserDto updateUser(String userName, UserDto sourceUser) throws Exception {
        User targetUser = userRepo.findByUserName(userName);
        if (nonNull(targetUser)) {
            try {
                setUserName(targetUser, sourceUser);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            setFavoriteTeam(targetUser, sourceUser);
            userRepo.save(targetUser);
            return userMapper.userEntityToDto(targetUser);
        }
        return null;
    }

    public boolean authenticateUser(String username, String givenPassword) {
        User user = userRepo.findByUserName(username);
        if (nonNull(user)) {
            String assignedPassword = user.getPassword();
            String hashedPassword = hashUserPassword(givenPassword);
            if (hashedPassword.equals(assignedPassword)) {
                return true;
            }
        }
        return false;
    }

    public void updatePassword(String userName, String updatedPassword) {
        User targetUser = userRepo.findByUserName(userName);
        if (nonNull(targetUser)) {
            String hashedPassword = hashUserPassword(updatedPassword);
            targetUser.setPassword(hashedPassword);
            userRepo.save(targetUser);
            log.info(String.format("Saving updated password for %s", targetUser.getUserName()));
        }
    }

    private void setUserName(User targetUser, UserDto sourceUser) throws Exception {
        if (nonNull(sourceUser.getUserName())) {
            User existingUserName = userRepo.findByUserName(sourceUser.getUserName());
            if (isNull(existingUserName)) {
                targetUser.setUserName(sourceUser.getUserName());
                log.info(String.format("User %s has updated username to: %s", targetUser.getId(), targetUser.getUserName()));
            } else {
                throw new Exception(String.format("Username: %s already exists", sourceUser.getUserName()));
            }
        }
    }

    private void setFavoriteTeam(User targetUser, UserDto sourceUser) {
        if (nonNull(sourceUser.getFavoriteTeam())) {
            targetUser.setFavoriteTeam(sourceUser.getFavoriteTeam());
        }
    }

    private String hashUserPassword(String userPassword) {
        String idForEncoder = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncoder, new BCryptPasswordEncoder());
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncoder, encoders);
        return passwordEncoder.encode(userPassword);
    }

}
