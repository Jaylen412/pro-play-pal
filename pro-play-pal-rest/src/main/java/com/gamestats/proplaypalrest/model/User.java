package com.gamestats.proplaypalrest.model;

import jakarta.persistence.*;
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
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @ManyToOne
    private Team favoriteTeam;
    private Instant createdDate;
}
