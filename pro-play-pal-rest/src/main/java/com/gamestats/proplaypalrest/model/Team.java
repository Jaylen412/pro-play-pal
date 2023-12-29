package com.gamestats.proplaypalrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "teams")
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID teamId;
    @Column(name = "team_key")
    private String key;
    private String city;
    private String name;
    private String conference;
    private String division;
    private String fullName;
    private String stadiumId;
}

/*
  {
    "Key": "ARI",
    "TeamID": 1,
    "PlayerID": 1,
    "City": "Arizona",
    "Name": "Cardinals",
    "Conference": "NFC",
    "Division": "West",
    "FullName": "Arizona Cardinals",
    "StadiumID": 29,
    "ByeWeek": 14,
    "GlobalTeamID": 1,
    "HeadCoach": "Jonathan Gannon",
    "PrimaryColor": "97233F",
    "SecondaryColor": "FFFFFF",
    "TertiaryColor": "000000",
    "QuaternaryColor": "A5ACAF",
    "WikipediaLogoURL": "https://upload.wikimedia.org/wikipedia/en/7/72/Arizona_Cardinals_logo.svg",
    "WikipediaWordMarkURL": "https://upload.wikimedia.org/wikipedia/commons/0/04/Arizona_Cardinals_wordmark.svg",
    "OffensiveCoordinator": "Drew Petzing",
    "DefensiveCoordinator": "Nick Rallis",
    "SpecialTeamsCoach": "Jeff Rodgers",
    "OffensiveScheme": "3WR",
    "DefensiveScheme": "3-4"
  }
 */
