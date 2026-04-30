package com.arthdroid1.clinica_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arthdroid1.clinica_api.models.entities.Appointment;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	boolean existsByProfessionalAndDateTime(Long professionalId, LocalDateTime dateTime);
	boolean existsByProfessionalIdAndDateTimeAndStatusNot(Long professionalId, LocalDateTime dateTime, AppointmentStatus status);
	boolean existsByPatientIdAndDateTimeAndStatusNot(Long professionalId, LocalDateTime dateTime, AppointmentStatus status );
	
	@Query("""
		    SELECT ap FROM Appointment ap
		    WHERE (:patientId IS NULL OR ap.patient.id = :patientId)
		      AND (:professionalId IS NULL OR ap.professional.id = :professionalId)
		      AND (:status IS NULL OR ap.status = :status)
		""")
	List<Appointment> findWithFilters(@Param("patientId") Long patientId, @Param("profesisonalId") Long professionalId, 
			@Param("status")AppointmentStatus status);

}
