package br.com.brokenbits.mvn.versions;

import java.io.IOException;

public class InvalidPOMException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPOMException() {
	}

	public InvalidPOMException(String message) {
		super(message);
	}

	public InvalidPOMException(Throwable cause) {
		super(cause);
	}

	public InvalidPOMException(String message, Throwable cause) {
		super(message, cause);
	}
}
