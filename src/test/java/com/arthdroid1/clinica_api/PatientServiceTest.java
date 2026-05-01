package com.arthdroid1.clinica_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.arthdroid1.clinica_api.dtos.PatientRequestDTO;
import com.arthdroid1.clinica_api.dtos.PatientResponseDTO;
import com.arthdroid1.clinica_api.exceptions.EmailAlreadyExistsException;
import com.arthdroid1.clinica_api.mappers.PatientMapper;
import com.arthdroid1.clinica_api.models.entities.Patient;
import com.arthdroid1.clinica_api.repositories.PatientRepository;
import com.arthdroid1.clinica_api.services.PatientService;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
	
	@Mock
	private PatientRepository patientRepository;
	
	@InjectMocks
	private PatientService patientService;

	@Test
	@DisplayName("Should create patient successfully")
	void shouldCreatePatientSuccessfully() {

	    PatientRequestDTO dto = new PatientRequestDTO(
	            "Jonas",
	            "jonas@gmail.com",
	            "81999999999",
	            LocalDate.of(1999, 1, 1)
	    );

	    Patient patient = PatientMapper.toEntity(dto);

	    when(patientRepository.existsByEmail(dto.email())).thenReturn(false);
	    when(patientRepository.save(any(Patient.class))).thenReturn(patient);

	    PatientResponseDTO response = patientService.registerPatient(dto);

	    assertNotNull(response);
	    assertEquals(dto.email(), response.email());

	    verify(patientRepository).save(any(Patient.class));
	}


	@Test
	@DisplayName("Should throw exception when email already exists")
	void shouldThrowExceptionWhenEmailAlreadyExists() {

	    PatientRequestDTO dto = new PatientRequestDTO(
	            "Jonas",
	            "jonas@gmail.com",
	            "81999999999",
	            LocalDate.of(1999, 1, 1)
	    );

	    when(patientRepository.existsByEmail(dto.email())).thenReturn(true);

	    assertThrows(EmailAlreadyExistsException.class, () -> {
	        patientService.registerPatient(dto);
	    });

	    verify(patientRepository, never()).save(any());
	}


	@Test
	@DisplayName("Should return list of patients")
	void shouldReturnListOfPatients() {

	    Patient patient = new Patient();
	    patient.setName("Jonas");
	    patient.setEmail("jonas@gmail.com");
	    patient.setTelephone("81999999999");
	    patient.setBirthDate(LocalDate.of(1999, 1, 1));

	    when(patientRepository.findAll()).thenReturn(List.of(patient));

	    List<PatientResponseDTO> result = patientService.listAllPatients();

	    assertFalse(result.isEmpty());
	    assertEquals(1, result.size());
	    assertEquals("Jonas", result.get(0).name());
	}

}
