package com.gamestats.proplaypalrest.service;

import com.gamestats.proplaypalrest.repo.TeamRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeamService {

    private TeamRepo teamRepo;

    TeamService(@Autowired TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }
}
