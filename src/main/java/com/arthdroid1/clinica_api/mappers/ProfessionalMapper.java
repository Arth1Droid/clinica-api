package com.arthdroid1.clinica_api.mappers;

import com.arthdroid1.clinica_api.dtos.ProfessionalRequestDTO;
import com.arthdroid1.clinica_api.dtos.ProfessionalResponseDTO;
import com.arthdroid1.clinica_api.models.entities.Professional;

public class ProfessionalMapper {
	
	public static Professional toEntity(ProfessionalRequestDTO dto) {
		return new Professional(
				dto.name(),
				dto.specialty(),
				dto.registrationNumber()
				);
	
	}
	
	public static ProfessionalResponseDTO toResponse(Professional professional) {
		return new ProfessionalResponseDTO(
				professional.getId(),
				professional.getName(),
				professional.getSpecialty(),
				professional.getRegistrationNumber()
				);
	}
}
