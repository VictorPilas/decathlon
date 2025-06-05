package com.vps.decathlon.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teammate {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private LocalDate startDate;
    private boolean active;
}
