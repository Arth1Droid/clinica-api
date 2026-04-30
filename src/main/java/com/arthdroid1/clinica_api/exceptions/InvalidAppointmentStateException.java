package com.arthdroid1.clinica_api.exceptions;

public class InvalidAppointmentStateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidAppointmentStateException(String message) {
		super(message);
	}

}
