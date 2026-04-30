package com.arthdroid1.clinica_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthdroid1.clinica_api.dtos.PatientRequestDTO;
import com.arthdroid1.clinica_api.dtos.PatientResponseDTO;
import com.arthdroid1.clinica_api.exceptions.EmailAlreadyExistsException;
import com.arthdroid1.clinica_api.mappers.PatientMapper;
import com.arthdroid1.clinica_api.models.entities.Patient;
import com.arthdroid1.clinica_api.repositories.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public PatientResponseDTO registerPatient(PatientRequestDTO dto) {

	    if (patientRepository.existsByEmail(dto.email())) {
	        throw new EmailAlreadyExistsException("Email already registered");
	    }
	    Patient patient = PatientMapper.toEntity(dto);
	    
	    patient = patientRepository.save(patient);
	    
	    return PatientMapper.toResponse(patient);
	}
	
	public List<PatientResponseDTO> listAllPatients(){
		return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toResponse)
                .toList();
	}

}
