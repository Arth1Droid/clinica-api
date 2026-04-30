package com.arthdroid1.clinica_api.dtos;

import java.time.LocalDateTime;

import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentType;

public record AppointmentResponseDTO(
	    Long id,
	    String patientName,
	    String professionalName,
	    LocalDateTime dateTime,
	    AppointmentStatus status,
	    AppointmentType type,
	    String cancellationReason
	) {}
