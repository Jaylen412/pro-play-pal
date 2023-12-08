package com.gamestats.proplaypalrest.model;

import java.time.Instant;
import java.util.UUID;

public class UserDto {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String userName;
    private Instant createdDate;
}
