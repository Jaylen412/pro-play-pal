package com.gamestats.proplaypalrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "team_key")
    @JsonProperty("Key")
    private String key;
    @Id
    @JsonProperty("TeamID")
    private Integer teamId;
    @JsonProperty("PlayerID")
    private Integer playerId;
    @JsonProperty("City")
    private String city;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Conference")
    private String conference;
    @JsonProperty("Division")
    private String division;
    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("StadiumID")
    private Integer stadiumId;
    @JsonProperty("GlobalTeamID")
    private String globalTeamId;
    @JsonProperty("HeadCoach")
    private String headCoach;
    @JsonProperty("WikipediaLogoURL")
    private String wikipediaLogoUrl;
    @JsonProperty("WikipediaWordMarkURL")
    private String wikipediaWordMarkUrl;
    @JsonProperty("OffensiveCoordinator")
    private String offensiveCoordinator;
    @JsonProperty("DefensiveCoordinator")
    private String defensiveCoordinator;
    @JsonProperty("SpecialTeamsCoach")
    private String specialTeamsCoach;
}

