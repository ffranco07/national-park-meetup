package com.cti.npmeetup.dto;

import org.springframework.http.HttpStatus;

import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.AppConstants;

/**
 * Error data transfer object
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public class ErrorDTO extends AbstractDTO {
	private static final String TAG = ErrorDTO.class.getSimpleName();
	
	private String message;
	private Integer httpStatusCode;
	private HttpStatus httpStatus;
	
	// ===================
	// Constructor(s)
	// ===================
	
	// No argument constructor
	public ErrorDTO() {
		super(TAG);
	}
	
	// Use this constructor for mock testing only since id is auto-generated by database
	public ErrorDTO(String message, HttpStatus httpStatus) {
		super(TAG);
		this.message = message;
		this.httpStatusCode = httpStatus.value();
		this.httpStatus = httpStatus;
	}
	
	// ===================
	// Public methods
	// ===================
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}
	
	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		ErrorDTO error = (ErrorDTO) o;
		
		return message.equals(error.message);
	}
	
	@Override
	public int hashCode() {
		return message.hashCode();
	}

	@Override
	public String toString() {
		return "ErrorDTO [message=" + message
			+ ", httpStatusCode=" + httpStatusCode
			+ ", httpStatus=" + (httpStatus != null ? httpStatus.toString() : "null")
			+ "]";
	}
}