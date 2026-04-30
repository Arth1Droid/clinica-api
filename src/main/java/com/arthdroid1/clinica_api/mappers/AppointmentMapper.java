package com.arthdroid1.clinica_api.mappers;

import com.arthdroid1.clinica_api.dtos.AppointmentResponseDTO;
import com.arthdroid1.clinica_api.models.entities.Appointment;

public class AppointmentMapper {

	public static AppointmentResponseDTO toResponse(Appointment appointment) {
        if (appointment == null) return null;

        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPatient().getName(),      
                appointment.getProfessional().getName(), 
                appointment.getDateTime(),
                appointment.getStatus(),
                appointment.getType(),        
                appointment.getCancellationReason()     
        );
    }
}
