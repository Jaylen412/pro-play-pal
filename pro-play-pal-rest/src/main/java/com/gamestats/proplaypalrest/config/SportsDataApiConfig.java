package com.gamestats.proplaypalrest.config;

import com.gamestats.proplaypalrest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SportsDataApiConfig {

    private RestTemplate restTemplate;
    private String apiUrl;

    public SportsDataApiConfig(@Autowired RestTemplate restTemplate,
                               @Value("${sports-data.urls.teams-basic}") String TeamApiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = TeamApiUrl;
    }
    public List<Team> getTeamData() {
        List<Team> teamData = new ArrayList<>();
        ResponseEntity<Team> response = restTemplate.getForEntity(apiUrl, Team.class, teamData);
        return teamData;
    }
}
