package com.vps.decathlon.domain.repository;

import com.vps.decathlon.domain.entity.TeammateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeammateRepository extends JpaRepository<TeammateEntity, UUID> {

    boolean existsByEmail(String email);
}
