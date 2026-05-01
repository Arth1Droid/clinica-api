package com.arthdroid1.clinica_api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.arthdroid1.clinica_api.dtos.AppointmentRequestDTO;
import com.arthdroid1.clinica_api.dtos.AppointmentResponseDTO;
import com.arthdroid1.clinica_api.exceptions.AppointmentDateException;
import com.arthdroid1.clinica_api.exceptions.AppointmentNotFoundException;
import com.arthdroid1.clinica_api.exceptions.InvalidAppointmentStateException;
import com.arthdroid1.clinica_api.exceptions.PatientNotFoundException;
import com.arthdroid1.clinica_api.exceptions.ProfessionalNotFoundException;
import com.arthdroid1.clinica_api.exceptions.ScheduleUnavailableException;
import com.arthdroid1.clinica_api.mappers.AppointmentMapper;
import com.arthdroid1.clinica_api.models.entities.Appointment;
import com.arthdroid1.clinica_api.models.entities.Patient;
import com.arthdroid1.clinica_api.models.entities.Professional;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;
import com.arthdroid1.clinica_api.repositories.AppointmentRepository;
import com.arthdroid1.clinica_api.repositories.PatientRepository;
import com.arthdroid1.clinica_api.repositories.ProfessionalRepository;

@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final ProfessionalRepository professionalRepository;
	private final PatientRepository patientRepository;
	
	private static final int MIN_INTERVAL_MINUTES = 30;
	
	public AppointmentService(AppointmentRepository appointmentRepository, ProfessionalRepository professionalRepository, PatientRepository patientRepository) {
		this.appointmentRepository = appointmentRepository;
		this.patientRepository = patientRepository;
		this.professionalRepository = professionalRepository;
	}
	
	public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto){
		validateDate(dto.dateTime());
	    
		Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        
        Professional professional = professionalRepository.findById(dto.professionalId())
                .orElseThrow(() -> new ProfessionalNotFoundException("Professional not found"));
        
        validateAvailability(dto, professional.getId(), patient.getId());
       
        Appointment appointment = new Appointment(
                patient,
                professional,
                dto.dateTime(),
                dto.type()
        );
        
        Appointment saved = appointmentRepository.save(appointment);
        return AppointmentMapper.toResponse(saved);
	}
	public List<AppointmentResponseDTO> listAppointments(Long patientId, Long professionalId, AppointmentStatus status){
		return appointmentRepository.findWithFilters(patientId, professionalId, status)
                .stream()
                .map(AppointmentMapper::toResponse) 
                .toList();
		
	}
	
	public void cancelAppointment(Long id, String reason) {
		Appointment existentAppointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
		
		if (existentAppointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new InvalidAppointmentStateException("Only scheduled appointments can be canceled.");
        }

        if (reason == null || reason.isBlank()) {
            throw new InvalidAppointmentStateException("Cancellation reason is required");
        }
		
		existentAppointment.cancel(reason);
		appointmentRepository.save(existentAppointment);
	}
	
	public void finishAppointment(Long id) {
		Appointment existentAppointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

		if (existentAppointment.getStatus() != AppointmentStatus.SCHEDULED) {
			throw new InvalidAppointmentStateException("Only scheduled appointments can be finished.");
		}
		
		if(existentAppointment.getDateTime().isAfter(LocalDateTime.now())) {
			throw new InvalidAppointmentStateException("Cannot finish appointment before its scheduled time");
		}
		existentAppointment.setStatus(AppointmentStatus.FINISHED);
		appointmentRepository.save(existentAppointment);
	}

	
	private void validateDate(LocalDateTime dateTime) {
		if (!dateTime.isAfter(LocalDateTime.now())) {
            throw new AppointmentDateException("The appointment must be in the future");
        }
	}
	
	private void validateAvailability(AppointmentRequestDTO dto, Long profId, Long patientId) {

		LocalDateTime start = dto.dateTime().minusMinutes(MIN_INTERVAL_MINUTES);
		LocalDateTime end = dto.dateTime().plusMinutes(MIN_INTERVAL_MINUTES);

		boolean isProfessionalBusy = appointmentRepository.existsByProfessionalIdAndDateTimeBetweenAndStatusNot(profId,
				start, end, AppointmentStatus.CANCELED);

		boolean isPatientBusy = appointmentRepository.existsByPatientIdAndDateTimeBetweenAndStatusNot(patientId, start,
				end, AppointmentStatus.CANCELED);

		if (isProfessionalBusy || isPatientBusy) {
			throw new ScheduleUnavailableException(
					"Must respect minimum interval of " + MIN_INTERVAL_MINUTES + " minutes");
		}
	}
	
}
	
