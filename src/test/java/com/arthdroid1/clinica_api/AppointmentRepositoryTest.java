package com.arthdroid1.clinica_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.arthdroid1.clinica_api.models.entities.Appointment;
import com.arthdroid1.clinica_api.models.entities.Patient;
import com.arthdroid1.clinica_api.models.entities.Professional;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentType;
import com.arthdroid1.clinica_api.repositories.AppointmentRepository;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
class AppointmentRepositoryTest {
	
	@Autowired
	AppointmentRepository repository;
	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("Should return a empty list when appointment filter find no one")
	void findWithFilters_ShouldReturnEmptyList() {
		List<Appointment> result = repository.findWithFilters(1L,2L,AppointmentStatus.FINISHED);
		assertTrue(result.isEmpty());
	}
	
	@Test
	@DisplayName("Should return appointment when filteringn by all params")
	void findWithFilters_ShouldReturnAppointmentWithAllFilters() {
		Patient patient = new Patient();
		patient.setName("Jonas");
		patient.setTelephone("81999999999");
		patient.setEmail("jonas@gmail.com");
		patient.setBirthDate(LocalDate.of(1999, 1, 31));
		
		Professional professional = new Professional();
		professional.setName("Dr. Maria bezerra");
		professional.setRegistrationNumber("CRM1234");
		professional.setSpecialty("cardiology");
		
		entityManager.persist(professional);
		entityManager.persist(patient);
		
		Appointment appointment = new Appointment();
		appointment.setProfessional(professional);
		appointment.setPatient(patient);
		appointment.setType(AppointmentType.CONSULTATION);
		appointment.setStatus(AppointmentStatus.SCHEDULED);
		appointment.setDateTime(LocalDateTime.now().plusDays(1));		
		entityManager.persist(appointment);
		
		List<Appointment> result = repository.findWithFilters(patient.getId(),professional.getId(),appointment.getStatus());
		
		assertFalse(result.isEmpty());
		assertEquals(patient.getId(), result.get(0).getPatient().getId());
		assertEquals(professional.getId(), result.get(0).getProfessional().getId());
		assertEquals(AppointmentStatus.SCHEDULED, result.get(0).getStatus());

	}
	
	@Test
	@DisplayName("Should return a appointment result when appointment filter find one patient")
	void findWithFilters_ShouldReturnAppointmentWithPatientFilter() {
		Patient patient = new Patient();
		patient.setName("Mariana");
		patient.setTelephone("81999999999");
		patient.setEmail("mariana@gmail.com");
		patient.setBirthDate(LocalDate.of(1999, 1, 31));
		
		Professional professional = new Professional();
		professional.setName("Dr. Marcos bezerra");
		professional.setRegistrationNumber("CRM1233");
		professional.setSpecialty("cardiology");
		
		entityManager.persist(professional);
		entityManager.persist(patient);
		
		Appointment appointment = new Appointment();
		appointment.setProfessional(professional);
		appointment.setPatient(patient);
		appointment.setType(AppointmentType.CONSULTATION);
		appointment.setStatus(AppointmentStatus.SCHEDULED);
		appointment.setDateTime(LocalDateTime.now().plusDays(1));		
		entityManager.persist(appointment);
		
		List<Appointment> result = repository.findWithFilters(patient.getId(), null,null);
		
		assertFalse(result.isEmpty());
		assertEquals(patient.getId(), result.get(0).getPatient().getId());

	}
	
	@Test
	@DisplayName("Should return all appointments when no filters are applied")
	void findWithFilters_ShouldReturnAllAppointments_WhenNoFilters() {

	    Patient patient = new Patient();
	    patient.setName("Jonas");
	    patient.setTelephone("81999999999");
	    patient.setEmail("jonas@gmail.com");
	    patient.setBirthDate(LocalDate.of(1999, 1, 31));

	    Professional professional = new Professional();
	    professional.setName("Dr. Maria");
	    professional.setRegistrationNumber("CRM1234");
	    professional.setSpecialty("cardiology");

	    entityManager.persist(patient);
	    entityManager.persist(professional);

	    Appointment appointment1 = new Appointment();
	    appointment1.setPatient(patient);
	    appointment1.setProfessional(professional);
	    appointment1.setStatus(AppointmentStatus.SCHEDULED);
	    appointment1.setType(AppointmentType.CONSULTATION);
	    appointment1.setDateTime(LocalDateTime.now().plusDays(1));

	    Appointment appointment2 = new Appointment();
	    appointment2.setPatient(patient);
	    appointment2.setProfessional(professional);
	    appointment2.setStatus(AppointmentStatus.FINISHED);
	    appointment2.setType(AppointmentType.EXAM);
	    appointment2.setDateTime(LocalDateTime.now().plusDays(2));


	    entityManager.persist(appointment1);
	    entityManager.persist(appointment2);

	    List<Appointment> result = repository.findWithFilters(null, null, null);

	    assertEquals(2, result.size());
	}
	
	
	

}
