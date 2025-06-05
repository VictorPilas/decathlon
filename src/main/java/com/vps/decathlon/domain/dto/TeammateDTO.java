package com.vps.decathlon.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeammateDTO {

    public UUID id;
    public String firstName;
    public String lastName;
    public String email;
    public String position;
    public LocalDate startDate;
    public boolean active;
}
