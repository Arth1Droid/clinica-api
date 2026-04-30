package com.arthdroid1.clinica_api.exceptions;

public class ProfessionalNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ProfessionalNotFoundException(String message) {
		super(message);
	}

}
