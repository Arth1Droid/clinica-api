package com.arthdroid1.clinica_api.mappers;

import com.arthdroid1.clinica_api.dtos.PatientRequestDTO;
import com.arthdroid1.clinica_api.dtos.PatientResponseDTO;
import com.arthdroid1.clinica_api.models.entities.Patient;

public class PatientMapper {
	
	public static Patient toEntity(PatientRequestDTO dto) {
		return new Patient(
				dto.name(),
				dto.email(),
				dto.telephone(),
				dto.birthDate()
				);
	
	}
	
	public static PatientResponseDTO toResponse(Patient patient) {
		return new PatientResponseDTO(
				patient.getId(),
				patient.getName(),
				patient.getEmail(),
				patient.getBirthDate()
				);
	}
}
