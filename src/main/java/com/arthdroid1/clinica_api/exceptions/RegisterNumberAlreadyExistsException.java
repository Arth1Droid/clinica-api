package com.arthdroid1.clinica_api.exceptions;

public class RegisterNumberAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RegisterNumberAlreadyExistsException(String message) {
		super(message);
	}

}
