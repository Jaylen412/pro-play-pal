package com.gamestats.proplaypalrest.controller;

import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import com.gamestats.proplaypalrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("create/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("details/user/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }
}
