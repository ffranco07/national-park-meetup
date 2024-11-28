package com.cti.npmeetup.controller;

import org.springframework.http.HttpStatus;

import com.cti.npmeetup.model.User;
import com.cti.npmeetup.dto.ErrorDTO;

/**
 * Abstract controller module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public abstract class AbstractController {

	// ===================
	// Constructor
	// ===================
	
	public AbstractController() {}
	
	// ===================
	// Protected Methods
	// ===================
	
	protected String getPlainMobileNumber(String mobileNumber) {
		if (mobileNumber != null) {
			return mobileNumber.replaceAll("[^0-9]", "");
		}
		return mobileNumber;
	}

	protected void setUserPlainMobileNumber(User user) {
		if (user.getMobileNumber() != null) {
			user.setMobileNumber(user.getMobileNumber().replaceAll("[^0-9]", ""));
		}
	}

	protected ErrorDTO createErrorDTO(String message, HttpStatus httpStatus) {
		return new ErrorDTO(message, httpStatus);
	}
}
