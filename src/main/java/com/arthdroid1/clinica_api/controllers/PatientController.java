package com.arthdroid1.clinica_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arthdroid1.clinica_api.dtos.PatientRequestDTO;
import com.arthdroid1.clinica_api.dtos.PatientResponseDTO;
import com.arthdroid1.clinica_api.services.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Patients",description="Endpoints for managing patients")
@RestController
@RequestMapping("/patients")
public class PatientController {
	
	private final PatientService patientService;
	
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@Operation(summary="Create a new patient")
	@PostMapping
	public ResponseEntity<PatientResponseDTO> create(@RequestBody @Valid PatientRequestDTO request){
		PatientResponseDTO response = patientService.registerPatient(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@Operation(summary="List all patients")
	@GetMapping
	public ResponseEntity<List<PatientResponseDTO>> findAll(){
		return ResponseEntity.ok(patientService.listAllPatients());
	}

}
