package com.gamestats.proplaypalrest.controller;

import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import com.gamestats.proplaypalrest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "create/user")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) throws Exception {
        UserDto createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "details/user/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

        @PostMapping(value = "update/user/{userId}")
    public UserDto updateUser(@PathVariable UUID userId, @RequestBody UserDto userDto) throws Exception {
        return userService.updateUser(userId, userDto);
    }

    @PostMapping(value = "update/password/{userName}")
    public ResponseEntity<String> updatePassword(@PathVariable String userName,@RequestParam String oldPassword, @RequestParam String updatedPassword) throws Exception {
        try {
            userService.updatePassword(userName, oldPassword, updatedPassword);
            return new ResponseEntity<>(String.format("Password has been updated for user: %s",userName), HttpStatus.OK);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<>("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "user/login")
    public ResponseEntity<String> validateUser(@RequestParam String userName, @RequestParam("password") String password) {
        boolean authenticated = userService.authenticateUser(userName, password);
       return authenticated ? new ResponseEntity<>("Authenticated", HttpStatus.OK) : new ResponseEntity<>("Un-authenticated", HttpStatus.BAD_REQUEST);
    }

}
