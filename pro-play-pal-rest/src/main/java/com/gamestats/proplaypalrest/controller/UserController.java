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

        @PostMapping(value = "update/user/{userName}")
    public UserDto updateUser(@PathVariable String userName, @RequestBody UserDto userDto) throws Exception {
        return userService.updateUser(userName, userDto);
    }

    @PostMapping(value = "update/password/{userName}")
    public ResponseEntity<String> updatePassword(@PathVariable String userName, @RequestParam String updatedPassword) {
        userService.updatePassword(userName, updatedPassword);
        return new ResponseEntity<>(String.format("Password has been updated for user: %s",userName), HttpStatus.OK);
    }

    // TODO: Authenticate user logins
    @GetMapping(value = "user/login")
    public boolean validateUser(@RequestParam String username, @RequestParam("password") String password) {
        boolean authenticated = userService.authenticateUser(username, password);
       return authenticated;
    }

}
