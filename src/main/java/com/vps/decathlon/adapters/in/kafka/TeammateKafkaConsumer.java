package com.vps.decathlon.adapters.in.kafka;

import com.vps.decathlon.domain.dto.TeammateDTO;
import com.vps.decathlon.domain.service.TeammateService;
import com.vps.decathlon.infrastructure.exception.TeammateAlreadyExistsException;
import com.vps.decathlon.infrastructure.mapper.TeammateControllerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TeammateKafkaConsumer {

    private final TeammateControllerMapper mapper;
    private final TeammateService service;

    @KafkaListener(topics = "teammate.created", groupId = "teammates", containerFactory = "kafkaListenerContainerFactory")
    public void listen(TeammateDTO dto) {
        try {
            service.createTeammate(mapper.fromDTO(dto));
            log.info("Teammate created from Kafka: {}", dto.getEmail());
        } catch (TeammateAlreadyExistsException e) {
            log.warn("Kafka - Duplicate Teammate : {}", dto.getEmail());
        } catch (Exception e) {
            log.error("Unexpected error processing Kafka message", e);
        }
    }
}
