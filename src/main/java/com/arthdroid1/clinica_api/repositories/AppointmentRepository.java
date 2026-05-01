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
	
	boolean existsByProfessionalIdAndDateTime(Long professionalId, LocalDateTime dateTime);
	boolean existsByProfessionalIdAndDateTimeBetweenAndStatusNot(Long professionalId, LocalDateTime start, LocalDateTime end, AppointmentStatus status);
	boolean existsByPatientIdAndDateTimeBetweenAndStatusNot(Long patientId, LocalDateTime start,LocalDateTime end, AppointmentStatus status );
	
	@Query("""
			SELECT ap FROM Appointment ap
			WHERE (:patientId IS NULL OR ap.patient.id = :patientId)
			AND (:status IS NULL OR ap.status = :status)
			AND (:professionalId IS NULL OR ap.professional.id = :professionalId)
			""")
			List<Appointment> findWithFilters(
			    @Param("patientId") Long patientId,
			    @Param("professionalId") Long professionalId,
			    @Param("status") AppointmentStatus status
			);
	
}
