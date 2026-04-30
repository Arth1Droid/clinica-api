package com.arthdroid1.clinica_api.exceptions;

public class AppointmentAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppointmentAlreadyExistsException(String message) {
		super(message);
	}

}
