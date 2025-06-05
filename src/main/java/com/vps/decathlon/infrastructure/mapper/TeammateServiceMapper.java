package com.vps.decathlon.infrastructure.mapper;

import com.vps.decathlon.domain.entity.TeammateEntity;
import com.vps.decathlon.domain.model.Teammate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeammateServiceMapper {

    Teammate fromEntity(TeammateEntity entity);
    List<Teammate> fromEntityList(List<TeammateEntity> entity);
    TeammateEntity toEntity(Teammate teammate);
}
