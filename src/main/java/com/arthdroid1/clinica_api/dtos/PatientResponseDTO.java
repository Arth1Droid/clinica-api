package com.arthdroid1.clinica_api.dtos;

import java.time.LocalDate;


public record PatientResponseDTO(
	    Long id,
	    String name,
	    String email,
	    LocalDate birthDate
	) {}
