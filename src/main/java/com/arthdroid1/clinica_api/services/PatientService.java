package com.arthdroid1.clinica_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthdroid1.clinica_api.models.entities.Patient;
import com.arthdroid1.clinica_api.models.exceptions.EmailAlreadyExistsException;
import com.arthdroid1.clinica_api.repositories.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public Patient registerPatient(Patient newPatient) {

	    if (patientRepository.existsByEmail(newPatient.getEmail())) {
	        throw new EmailAlreadyExistsException("Email already in use");
	    }
	    return patientRepository.save(newPatient);
	}
	
	public List<Patient> listAllPatients(){
		return patientRepository.findAll();
	}

}
