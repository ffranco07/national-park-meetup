package com.cti.npmeetup.exception;

//import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;

/**
 * Data store exception module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public final class DataStoreException extends BaseException {
	private final HttpStatus httpStatus;
	//public boolean _isRollback = false;
	
	// ==============
	// Constructors
	// ==============

	public DataStoreException(String msg, HttpStatus httpStatus) {
		super(new Exception(msg));
		this.httpStatus = httpStatus;
	}
	
	public DataStoreException(Exception e, HttpStatus httpStatus) {
		super(e);
		this.httpStatus = httpStatus;
		//if (e instanceof PersistenceException) {
		//_isRollback = true;
		//}
	
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	//public boolean getIsRollback() {
	//return _isRollback;
	//}
}
