package com.arthdroid1.clinica_api.exceptions;

public class AppointmentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppointmentNotFoundException(String message) {
		super(message);
	}

}
