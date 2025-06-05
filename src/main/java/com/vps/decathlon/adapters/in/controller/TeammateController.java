package com.vps.decathlon.adapters.in.controller;

import com.vps.decathlon.domain.dto.TeammateDTO;
import com.vps.decathlon.domain.service.TeammateService;
import com.vps.decathlon.infrastructure.mapper.TeammateControllerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teammates")
@AllArgsConstructor
public class TeammateController {

    private final TeammateService service;
    private final TeammateControllerMapper mapper;

    @GetMapping
    public ResponseEntity<List<TeammateDTO>> getAll() {
        return ResponseEntity.ok(mapper.toDTOList(service.getAllTeammates()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeammateDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toDTO(service.getTeammate(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeammateDTO> update(@PathVariable UUID id, @RequestBody TeammateDTO dto) {
        return ResponseEntity.ok(mapper.toDTO(service.updateTeammate(id, mapper.fromDTO(dto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteTeammate(id);
        return ResponseEntity.noContent().build();
    }
}
