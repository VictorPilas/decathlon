package com.vps.decathlon.adapters.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vps.decathlon.domain.dto.TeammateDTO;
import com.vps.decathlon.domain.model.Teammate;
import com.vps.decathlon.domain.service.TeammateService;
import com.vps.decathlon.infrastructure.mapper.TeammateControllerMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TeammateController.class)
class TeammateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TeammateService service;

    @MockitoBean
    private TeammateControllerMapper mapper;

    @BeforeEach
    void setUp() {
        reset(service, mapper);
    }

    @Test
    void getAll_shouldReturnListOfTeammateDTOs() throws Exception {

        List<Teammate> teammates = Instancio.ofList(Teammate.class).size(3).create();
        List<TeammateDTO> teammateDTOs = Instancio.ofList(TeammateDTO.class).size(3).create();

        when(service.getAllTeammates()).thenReturn(teammates);
        when(mapper.toDTOList(teammates)).thenReturn(teammateDTOs);

        mockMvc.perform(get("/api/teammates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(teammateDTOs.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].firstName", is(teammateDTOs.get(0).getFirstName())));

        verify(service, times(1)).getAllTeammates();
        verify(mapper, times(1)).toDTOList(teammates);
        verifyNoMoreInteractions(service, mapper);
    }

    @Test
    void getById_shouldReturnTeammateDTO() throws Exception {

        UUID teammateId = UUID.randomUUID();
        Teammate teammate = Instancio.create(Teammate.class);
        TeammateDTO teammateDTO = Instancio.create(TeammateDTO.class);

        teammateDTO.setId(teammateId);

        when(service.getTeammate(teammateId)).thenReturn(teammate);
        when(mapper.toDTO(teammate)).thenReturn(teammateDTO);

        mockMvc.perform(get("/api/teammates/{id}", teammateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(teammateId.toString())))
                .andExpect(jsonPath("$.firstName", is(teammateDTO.getFirstName())));

        verify(service, times(1)).getTeammate(teammateId);
        verify(mapper, times(1)).toDTO(teammate);
        verifyNoMoreInteractions(service, mapper);
    }

    @Test
    void update_shouldUpdateTeammateAndReturnUpdatedDTO() throws Exception {

        UUID teammateId = UUID.randomUUID();
        TeammateDTO requestDTO = Instancio.create(TeammateDTO.class);

        requestDTO.setId(teammateId);

        Teammate mappedTeammate = Instancio.create(Teammate.class);
        Teammate updatedTeammate = Instancio.create(Teammate.class);
        TeammateDTO responseDTO = Instancio.create(TeammateDTO.class);
        responseDTO.setId(teammateId);

        when(mapper.fromDTO(any(TeammateDTO.class))).thenReturn(mappedTeammate);
        when(service.updateTeammate(eq(teammateId), any(Teammate.class))).thenReturn(updatedTeammate);
        when(mapper.toDTO(updatedTeammate)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/teammates/{id}", teammateId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(teammateId.toString())))
                .andExpect(jsonPath("$.firstName", is(responseDTO.getFirstName())));

        // Verify mocks
        verify(mapper, times(1)).fromDTO(any(TeammateDTO.class));
        verify(service, times(1)).updateTeammate(eq(teammateId), any(Teammate.class));
        verify(mapper, times(1)).toDTO(updatedTeammate);
        verifyNoMoreInteractions(service, mapper);
    }

    @Test
    void delete_shouldDeleteTeammateAndReturnNoContent() throws Exception {
        UUID teammateId = UUID.randomUUID();

        doNothing().when(service).deleteTeammate(teammateId);

        mockMvc.perform(delete("/api/teammates/{id}", teammateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteTeammate(teammateId);
        verifyNoMoreInteractions(service, mapper);
    }
}
