package com.arthdroid1.clinica_api.exceptions;

public class ScheduleUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ScheduleUnavailableException(String message) {
		super(message);
	}

}
