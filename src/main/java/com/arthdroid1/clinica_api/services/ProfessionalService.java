package com.arthdroid1.clinica_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arthdroid1.clinica_api.dtos.ProfessionalRequestDTO;
import com.arthdroid1.clinica_api.dtos.ProfessionalResponseDTO;
import com.arthdroid1.clinica_api.exceptions.RegisterNumberAlreadyExistsException;
import com.arthdroid1.clinica_api.mappers.ProfessionalMapper;
import com.arthdroid1.clinica_api.models.entities.Professional;
import com.arthdroid1.clinica_api.repositories.ProfessionalRepository;

@Service
public class ProfessionalService {
	
	private final ProfessionalRepository professionalRepository;
	
	public ProfessionalService(ProfessionalRepository professionalRepository) {
		this.professionalRepository = professionalRepository;
	}
	
	public ProfessionalResponseDTO registerProfessional(ProfessionalRequestDTO dto) {

	    boolean alreadyExistantRegistration = professionalRepository.existsByRegistrationNumber(dto.registrationNumber());
	    
	    if (alreadyExistantRegistration) {
	    	throw new RegisterNumberAlreadyExistsException("Registration number already exists");
	    }
	    Professional professional = ProfessionalMapper.toEntity(dto);
	    
	    Professional saved = professionalRepository.save(professional);
	    
	    return ProfessionalMapper.toResponse(saved);
	}
	
	public List<ProfessionalResponseDTO> listAllProfessionals(){
		return professionalRepository.findAll()
                .stream()
                .map(ProfessionalMapper::toResponse)
                .toList();
	}

}
