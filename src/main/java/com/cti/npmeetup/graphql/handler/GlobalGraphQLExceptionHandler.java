package com.cti.npmeetup.graphql.handler;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.ResponseEntity;

import com.cti.npmeetup.exception.DataStoreGraphQLException;

/**
 * GraphQL global exception handler module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@ControllerAdvice
public class GlobalGraphQLExceptionHandler {
	
	// Handle DataStoreGraphQLException globally
	@ExceptionHandler(DataStoreGraphQLException.class)
	public ResponseEntity<String> handleDataStoreGraphQLException(DataStoreGraphQLException ex) {
		// Here, you can handle the exception specifically and return a GraphQL-specific response
		return ResponseEntity.status(500) // GraphQL typically returns 500 for server errors
			.body("Custom error: " + ex.getMessage());
	}
}
