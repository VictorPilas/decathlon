package com.vps.decathlon.infrastructure.mapper;

import com.vps.decathlon.domain.dto.TeammateDTO;
import com.vps.decathlon.domain.model.Teammate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeammateControllerMapper {

    TeammateDTO toDTO(Teammate teammate);
    List<TeammateDTO> toDTOList(List<Teammate> teammates);
    Teammate fromDTO(TeammateDTO dto);

}
