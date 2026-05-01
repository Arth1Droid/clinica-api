package com.arthdroid1.clinica_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arthdroid1.clinica_api.dtos.ProfessionalRequestDTO;
import com.arthdroid1.clinica_api.dtos.ProfessionalResponseDTO;
import com.arthdroid1.clinica_api.services.ProfessionalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Professionals",description="Endpoints for managing professionals")
@RestController
@RequestMapping("/professionals")
public class ProfessionalController {
	
	private final ProfessionalService professionalService;
	
	public ProfessionalController(ProfessionalService professionalService) {
		this.professionalService = professionalService;
	}
	
	@Operation(summary="Create a new professional")
	@PostMapping
	public ResponseEntity<ProfessionalResponseDTO> create(@RequestBody @Valid ProfessionalRequestDTO request){
		ProfessionalResponseDTO response = professionalService.registerProfessional(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@Operation(summary="List all professionals")
	@GetMapping
	public ResponseEntity<List<ProfessionalResponseDTO>> findAll(){
		return ResponseEntity.ok(professionalService.listAllProfessionals());
	}
	

}
