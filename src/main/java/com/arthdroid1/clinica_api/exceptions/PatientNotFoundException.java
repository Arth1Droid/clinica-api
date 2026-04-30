package com.arthdroid1.clinica_api.exceptions;

public class PatientNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PatientNotFoundException(String message) {
		super(message);
	}

}
