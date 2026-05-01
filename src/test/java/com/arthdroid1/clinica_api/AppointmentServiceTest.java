package com.arthdroid1.clinica_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.arthdroid1.clinica_api.dtos.AppointmentRequestDTO;
import com.arthdroid1.clinica_api.dtos.AppointmentResponseDTO;
import com.arthdroid1.clinica_api.exceptions.AppointmentDateException;
import com.arthdroid1.clinica_api.exceptions.InvalidAppointmentStateException;
import com.arthdroid1.clinica_api.exceptions.ScheduleUnavailableException;
import com.arthdroid1.clinica_api.models.entities.Appointment;
import com.arthdroid1.clinica_api.models.entities.Patient;
import com.arthdroid1.clinica_api.models.entities.Professional;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentType;
import com.arthdroid1.clinica_api.repositories.AppointmentRepository;
import com.arthdroid1.clinica_api.repositories.PatientRepository;
import com.arthdroid1.clinica_api.repositories.ProfessionalRepository;
import com.arthdroid1.clinica_api.services.AppointmentService;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

	@Mock
	private AppointmentRepository appointmentRepository;

	@Mock
	private ProfessionalRepository professionalRepository;

	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private AppointmentService appointmentService;
	
	@Test
	void shouldThrowException_whenProfessionalHasAnotherAppointmentAtSameTime() {
	    // Arrange
	    AppointmentRequestDTO dto = new AppointmentRequestDTO(
	            1L, 2L, LocalDateTime.now().plusDays(1), AppointmentType.CONSULTATION
	    );

	    when(patientRepository.findById(1L)).thenReturn(Optional.of(new Patient()));
	    when(professionalRepository.findById(2L)).thenReturn(Optional.of(new Professional()));

	    when(appointmentRepository.existsByProfessionalIdAndDateTimeAndStatusNot(any(), any(), any()))
	            .thenReturn(true);

	    // Act & Assert
	    assertThrows(ScheduleUnavailableException.class,
	            () -> appointmentService.createAppointment(dto));
	}

	@Test
	void shouldThrowException_whenDateIsInPast() {
	    AppointmentRequestDTO dto = new AppointmentRequestDTO(
	            1L, 2L, LocalDateTime.now().minusDays(1), AppointmentType.CONSULTATION
	    );

	    assertThrows(AppointmentDateException.class,
	            () -> appointmentService.createAppointment(dto));
	}

	@Test
	void shouldThrowException_whenCancelWithoutReason() {
	    Appointment appointment = new Appointment();
	    appointment.setStatus(AppointmentStatus.SCHEDULED);

	    when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

	    assertThrows(InvalidAppointmentStateException.class,
	            () -> appointmentService.cancelAppointment(1L, ""));
	}

	@Test
	void shouldCancelAppointmentAndUpdateStatus() {
	    Appointment appointment = new Appointment();
	    appointment.setStatus(AppointmentStatus.SCHEDULED);

	    when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

	    appointmentService.cancelAppointment(1L, "Patient request");

	    assertEquals(AppointmentStatus.CANCELED, appointment.getStatus());
	    verify(appointmentRepository).save(appointment);
	}


	@Test
	void shouldReturnFilteredAppointments() {
	    // Arrange
	    Patient patient = new Patient();
	    patient.setName("João");

	    Professional professional = new Professional();
	    professional.setName("Dr. Maria");

	    Appointment appointment = new Appointment();
	    appointment.setPatient(patient);
	    appointment.setProfessional(professional);
	    appointment.setStatus(AppointmentStatus.SCHEDULED);

	    when(appointmentRepository.findWithFilters(1L, 2L, AppointmentStatus.SCHEDULED))
	            .thenReturn(List.of(appointment));

	    // Act
	    List<AppointmentResponseDTO> result =
	            appointmentService.listAppointments(1L, 2L, AppointmentStatus.SCHEDULED);

	    // Assert
	    assertFalse(result.isEmpty());
	}

}
