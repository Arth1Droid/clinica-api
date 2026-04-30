package com.arthdroid1.clinica_api.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arthdroid1.clinica_api.models.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	boolean existsByProfessionalAndDateTime(Long professionalId, LocalDateTime dateTime);

}
