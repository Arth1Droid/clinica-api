package com.arthdroid1.clinica_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arthdroid1.clinica_api.dtos.AppointmentRequestDTO;
import com.arthdroid1.clinica_api.dtos.AppointmentResponseDTO;
import com.arthdroid1.clinica_api.dtos.CancelAppointmentRequestDTO;
import com.arthdroid1.clinica_api.models.entities.enums.AppointmentStatus;
import com.arthdroid1.clinica_api.services.AppointmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Appointments",description="Endpoints for managing appointments")
@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	
	private final AppointmentService appointmentService;

	public AppointmentController(AppointmentService appointmentService) {
	    this.appointmentService = appointmentService;
	}
	@Operation(summary="Create a new appointment")
	@PostMapping
	public ResponseEntity<AppointmentResponseDTO> create(@RequestBody @Valid AppointmentRequestDTO request){
		AppointmentResponseDTO response = appointmentService.createAppointment(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Operation(summary="List appointments by optional filters")
	@GetMapping
	public ResponseEntity<List<AppointmentResponseDTO>> listAppointments(@RequestParam(required = false)Long patientId,@RequestParam(required = false)Long professionalId, @RequestParam(required = false)AppointmentStatus status){
		return ResponseEntity.ok(appointmentService.listAppointments(patientId, professionalId, status));
	}
	
	@Operation(summary="Cancel a appointment")
	@PatchMapping("/{id}/cancel")
	public ResponseEntity<Void> cancel(@PathVariable Long id, @RequestBody @Valid CancelAppointmentRequestDTO request){
		 appointmentService.cancelAppointment(id, request.reason());
		   return ResponseEntity.noContent().build();
	}
	@Operation(summary="Finish a appointment")
	@PatchMapping("/{id}/finish")
	public ResponseEntity<Void> finish(@PathVariable Long id){
		 appointmentService.finishAppointment(id);
		   return ResponseEntity.noContent().build();
	}

}
