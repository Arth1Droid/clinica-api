package com.arthdroid1.clinica_api.dtos;

import java.time.LocalDateTime;

import com.arthdroid1.clinica_api.models.entities.enums.AppointmentType;

import jakarta.validation.constraints.NotNull;

public record AppointmentRequestDTO(
	    @NotNull Long patientId,
	    @NotNull Long professionalId,
	    @NotNull LocalDateTime dateTime,
	    @NotNull AppointmentType type
	) {}
