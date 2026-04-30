package com.arthdroid1.clinica_api.models.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
	
	public EmailAlreadyExistsException(String message) {
		super(message);
	}

}
