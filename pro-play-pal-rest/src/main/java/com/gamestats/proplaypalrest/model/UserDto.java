package com.gamestats.proplaypalrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String userName;
    private UserRole userRole;
    private Team favoriteTeam;
    private Instant createdDate;
}
