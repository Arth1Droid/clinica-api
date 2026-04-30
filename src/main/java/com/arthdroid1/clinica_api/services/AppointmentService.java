package com.arthdroid1.clinica_api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthdroid1.clinica_api.exceptions.AppointmentDateException;
import com.arthdroid1.clinica_api.exceptions.AppointmentNotFoundException;
import com.arthdroid1.clinica_api.exceptions.InvalidAppointmentStateException;
import com.arthdroid1.clinica_api.exceptions.ScheduleUnavailableException;
import com.arthdroid1.clinica_api.models.entities.Appointment;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;
import com.arthdroid1.clinica_api.repositories.AppointmentRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public Appointment createAppointment(Appointment newAppointment){
		validateDate(newAppointment);
	    
	    boolean isProfessionalBusy = appointmentRepository.existsByProfessionalIdAndDateTimeAndStatusNot(
	    		newAppointment.getProfessional().getId(), newAppointment.getDateTime(), 
	    		AppointmentStatus.CANCELED);
	    
	    boolean isPatientBusy = appointmentRepository.existsByPatientIdAndDateTimeAndStatusNot(
	    		newAppointment.getPatient().getId(), newAppointment.getDateTime(), 
	    		AppointmentStatus.CANCELED);
	    
	    if(isProfessionalBusy || isPatientBusy) {
	    	throw new ScheduleUnavailableException("Schedule unavailable");
	    }
	    
		return  appointmentRepository.save(newAppointment);
	}
	public List<Appointment> listAppointments(Long patientId, Long professionalId, AppointmentStatus satus){
		return appointmentRepository.findWithFilters(patientId, professionalId, satus);
		
	}
	
	public void cancelAppointment(Long id, String reason) {
		Appointment existentAppointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
		AppointmentStatus status = existentAppointment.getStatus();
		
		if(status == AppointmentStatus.CANCELED || status== AppointmentStatus.FINISHED) {
			throw new InvalidAppointmentStateException("Cannot cancel an appointment that is already canceled or finished.");
		}
		if (reason == null || reason.isBlank()) {
		    throw new InvalidAppointmentStateException("Cancellation reason is required");
		}
		
		existentAppointment.cancel(reason);
		appointmentRepository.save(existentAppointment);
	}
	
	private void validateDate(Appointment appointment) {
	    if (!appointment.getDateTime().isAfter(LocalDateTime.now())) {
	        throw new AppointmentDateException("The appointment must be in the future");
	    }
	}
	
}
	
