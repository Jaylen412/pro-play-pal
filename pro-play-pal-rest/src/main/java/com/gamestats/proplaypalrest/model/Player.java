package com.gamestats.proplaypalrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Table(name = "players")
@Entity
public class Player {

    @Id
    @JsonProperty("PlayerID")
    private Integer playerId;
    @JsonProperty("Team")
    private String team;
    @JsonProperty("Number")
    private Integer number;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Position")
    private String position;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Height")
    private String height;
    @JsonProperty("Weight")
    private Integer weight;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Age")
    private Integer age;
    @JsonProperty("BirthDateString")
    private String birthDateString;
    @JsonProperty("PhotoUrl")
    private String photoUrl;
    @JsonProperty("TeamID")
    private Integer teamId;
}
