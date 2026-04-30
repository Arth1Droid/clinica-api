package com.arthdroid1.clinica_api.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatientRequestDTO(
	    @NotBlank String name,
	    @NotBlank @Email String email,
	    @NotBlank @Pattern(regexp = "\\d{10,11}") String telephone,
	    @NotNull LocalDate birthDate
	) {}
