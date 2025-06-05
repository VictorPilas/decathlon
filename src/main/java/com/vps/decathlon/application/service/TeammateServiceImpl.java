package com.vps.decathlon.application.service;

import com.vps.decathlon.domain.entity.TeammateEntity;
import com.vps.decathlon.domain.model.Teammate;
import com.vps.decathlon.domain.repository.TeammateRepository;
import com.vps.decathlon.domain.service.TeammateService;
import com.vps.decathlon.infrastructure.exception.TeammateAlreadyExistsException;
import com.vps.decathlon.infrastructure.exception.TeammateNotFoundException;
import com.vps.decathlon.infrastructure.mapper.TeammateServiceMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeammateServiceImpl implements TeammateService {

    private final TeammateRepository repository;
    private final TeammateServiceMapper mapper;


    @Override
    public void createTeammate(Teammate teammate) {
        if (repository.existsByEmail(teammate.getEmail())) {
            throw new TeammateAlreadyExistsException("Email already exists: " + teammate.getEmail());
        }
        mapper.fromEntity(repository.save(mapper.toEntity(teammate)));
    }

    @Override
    public Teammate getTeammate(UUID id) {
        Optional<TeammateEntity> optionalTeammateEntity = repository.findById(id);
        optionalTeammateEntity.orElseThrow(() -> new TeammateNotFoundException("Teammate with id: " + id));
        return mapper.fromEntity(optionalTeammateEntity.get());
    }

    @Override
    public List<Teammate> getAllTeammates() {
        return mapper.fromEntityList(repository.findAll());
    }

    @Override
    public Teammate updateTeammate(UUID id, Teammate updated) {
        Optional<TeammateEntity> optionalTeammateEntity = repository.findById(id);
        optionalTeammateEntity.orElseThrow(() -> new TeammateNotFoundException("Teammate with id: " + id));
        updated.setId(id);
        return mapper.fromEntity(repository.save(mapper.toEntity(updated)));
    }

    @Override
    public void deleteTeammate(UUID id) {
        if(!repository.existsById(id)){
            throw new TeammateNotFoundException("Teammate with id: " + id);
        }
        repository.deleteById(id);
    }
}
