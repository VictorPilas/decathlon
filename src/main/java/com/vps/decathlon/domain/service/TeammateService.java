package com.vps.decathlon.domain.service;

import com.vps.decathlon.domain.model.Teammate;

import java.util.List;
import java.util.UUID;

public interface TeammateService {
    void createTeammate(Teammate teammate);
    Teammate getTeammate(UUID id);
    List<Teammate> getAllTeammates();
    Teammate updateTeammate(UUID id, Teammate updated);
    void deleteTeammate(UUID id);
}
