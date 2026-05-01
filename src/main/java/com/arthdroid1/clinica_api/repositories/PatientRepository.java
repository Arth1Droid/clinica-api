package com.arthdroid1.clinica_api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arthdroid1.clinica_api.models.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	boolean existsByEmail(String email);

}
