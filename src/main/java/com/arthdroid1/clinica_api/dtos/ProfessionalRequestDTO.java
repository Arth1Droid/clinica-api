package com.arthdroid1.clinica_api.dtos;


import jakarta.validation.constraints.NotBlank;


public record ProfessionalRequestDTO(
	    @NotBlank String name,
	    @NotBlank String specialty,
	    @NotBlank String registrationNumber

	    
	) {}
