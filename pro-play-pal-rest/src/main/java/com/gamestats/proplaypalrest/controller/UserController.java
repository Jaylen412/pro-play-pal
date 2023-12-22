package com.gamestats.proplaypalrest.controller;

import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import com.gamestats.proplaypalrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    // TODO: Authenticate user logins
    @GetMapping(value = "user/login")
    public boolean validateUser(@RequestParam String username, @RequestParam("password") String password) {
        boolean authenticated = userService.authenticateUser(username, password);
       return authenticated;
    }

    // TODO: functionality for updating a user
//    @PostMapping(value = "update/user/{userId}")
//    public UserDto updateUser(@PathVariable UUID userId, UserDto userDto) {
//        userDto.setUserId(userId);
//        return userService.updateUser(userDto);
//    }
}
