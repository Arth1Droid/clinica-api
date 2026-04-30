package com.arthdroid1.clinica_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record CancelAppointmentRequestDTO( @NotBlank String reason) {

}
