package com.gamestats.proplaypalrest.repo;

import com.gamestats.proplaypalrest.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TeamRepo extends JpaRepository<Team, UUID> {
}
