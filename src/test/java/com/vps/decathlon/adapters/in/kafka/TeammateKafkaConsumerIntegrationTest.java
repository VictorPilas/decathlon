package com.vps.decathlon.adapters.in.kafka;

import com.vps.decathlon.domain.dto.TeammateDTO;
import com.vps.decathlon.domain.model.Teammate;
import com.vps.decathlon.domain.service.TeammateService;
import com.vps.decathlon.infrastructure.mapper.TeammateControllerMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "teammate.created")
@DirtiesContext
@ActiveProfiles("test")
public class TeammateKafkaConsumerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, TeammateDTO> kafkaTemplate;

    @MockBean
    private TeammateService service;

    @MockBean
    private TeammateControllerMapper mapper;

    @Test
    void shouldConsumeAndCallCreateTeammate() {
        TeammateDTO dto = Instancio.create(TeammateDTO.class);
        Teammate teammate = Instancio.create(Teammate.class);
        when(mapper.fromDTO(any())).thenReturn(teammate);

        kafkaTemplate.send("teammate.created", dto);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            verify(mapper).fromDTO(any());
            verify(service).createTeammate(any());
        });
    }

}
