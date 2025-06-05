// Ubicación: src/test/java/com/vps/decathlon/application/service/TeammateServiceImplTest.java

package com.vps.decathlon.application.service;

import com.vps.decathlon.domain.entity.TeammateEntity;
import com.vps.decathlon.domain.model.Teammate;
import com.vps.decathlon.domain.repository.TeammateRepository;
import com.vps.decathlon.infrastructure.exception.TeammateAlreadyExistsException;
import com.vps.decathlon.infrastructure.exception.TeammateNotFoundException;
import com.vps.decathlon.infrastructure.mapper.TeammateServiceMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeammateServiceImplTest {

    @Mock
    private TeammateRepository repository;

    @Mock
    private TeammateServiceMapper mapper;

    @InjectMocks
    private TeammateServiceImpl service;

    private Teammate domain;
    private TeammateEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        domain = Instancio.create(Teammate.class);
        entity = Instancio.create(TeammateEntity.class);
    }

    @Test
    void shouldCreateTeammateSuccessfully() {
        when(repository.existsByEmail(domain.getEmail())).thenReturn(false);
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(mapper.fromEntity(entity)).thenReturn(domain);

        service.createTeammate(domain);

        verify(repository).save(any());
    }

    @Test
    void shouldThrowExceptionIfEmailAlreadyExists() {
        when(repository.existsByEmail(domain.getEmail())).thenReturn(true);

        assertThrows(TeammateAlreadyExistsException.class, () -> service.createTeammate(domain));
    }

    @Test
    void shouldReturnTeammateById() {
        when(repository.findById(domain.getId())).thenReturn(Optional.of(entity));
        when(mapper.fromEntity(entity)).thenReturn(domain);

        Teammate result = service.getTeammate(domain.getId());

        assertThat(result).isEqualTo(domain);
    }

    @Test
    void shouldThrowIfTeammateNotFoundById() {
        when(repository.findById(domain.getId())).thenReturn(Optional.empty());

        assertThrows(TeammateNotFoundException.class, () -> service.getTeammate(domain.getId()));
    }

    @Test
    void shouldReturnAllTeammates() {
        List<TeammateEntity> entities = List.of(entity);
        List<Teammate> domains = List.of(domain);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.fromEntityList(entities)).thenReturn(domains);

        List<Teammate> result = service.getAllTeammates();

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldUpdateExistingTeammate() {
        when(repository.findById(domain.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(mapper.fromEntity(entity)).thenReturn(domain);

        Teammate updated = service.updateTeammate(domain.getId(), domain);

        assertThat(updated).isEqualTo(domain);
    }

    @Test
    void shouldThrowIfUpdateNonexistentTeammate() {
        when(repository.findById(domain.getId())).thenReturn(Optional.empty());

        assertThrows(TeammateNotFoundException.class, () -> service.updateTeammate(domain.getId(), domain));
    }

    @Test
    void shouldDeleteExistingTeammate() {
        when(repository.existsById(domain.getId())).thenReturn(true);

        service.deleteTeammate(domain.getId());

        verify(repository).deleteById(domain.getId());
    }

    @Test
    void shouldThrowIfDeleteNonexistentTeammate() {
        when(repository.existsById(domain.getId())).thenReturn(false);

        assertThrows(TeammateNotFoundException.class, () -> service.deleteTeammate(domain.getId()));
    }
}
