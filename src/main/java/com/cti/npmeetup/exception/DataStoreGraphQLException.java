package com.cti.npmeetup.exception;

/**
 * Data store GraphQL exception module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public final class DataStoreGraphQLException extends BaseException {
	
	// ==============
	// Constructors
	// ==============

	/**
	 * Default constructor.
	 */
	public DataStoreGraphQLException() {
		super();
	}
	
	/**
	 * Constructs a new DataStoreGraphQLException with the specified error message.
	 *
	 * @param message the error message
	 */
	public DataStoreGraphQLException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new DataStoreGraphQLException with the specified error message and nested exception.
	 *
	 * @param message the error message
	 * @param cause   the nested exception
	 */
	public DataStoreGraphQLException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a new DataStoreGraphQLException with the specified nested exception.
	 *
	 * @param cause the nested exception
	 */
	public DataStoreGraphQLException(Throwable cause) {
		super(cause);
	}
}
