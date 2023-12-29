package com.gamestats.proplaypalrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String userName;
    private UserRole userRole;
    private Team favoriteTeam;
    private Instant createdDate;
}
