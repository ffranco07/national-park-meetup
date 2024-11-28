package com.cti.npmeetup.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * LoggerUtil is a class used to log class data
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Component
public final class LogUtil {

	// Define logger instances at the class level
	private static final Logger MESSAGE_LOGGER = LoggerFactory.getLogger(AppConstants.MESSAGE_LOGGER);
	private static final Logger ERROR_LOGGER = LoggerFactory.getLogger(AppConstants.ERROR_LOGGER);
	private static final Logger SERVICE_LOGGER = LoggerFactory.getLogger(AppConstants.SERVICE_LOGGER);
	private static final Logger TEST_LOGGER = LoggerFactory.getLogger(AppConstants.TEST_LOGGER);
	
	// ================
	// Constructor
	// ================
	
	// Prevent instantiation
	private LogUtil() {}
		
	// ================
	// Public methods
	// ================

	// Method to log via message logger
	public static void messageLogger(String className, String message) {
		MESSAGE_LOGGER.debug(className + " - " + message);
	}
	
	// Method to log via error logger
	public static void errorLogger(String className, String errorMessage) {
		ERROR_LOGGER.error(className + " - " + errorMessage);
	}
	
	// Method to log via error logger
	public static void errorLogger(String className, String message, Throwable exception) {
		ERROR_LOGGER.error(className + " - " + message, exception);
	}
	
	// Method to log via service logger
	public static void serviceLogger(String className, String message) {
		SERVICE_LOGGER.debug(className + " - " + message, message);
	}

	// Method to log via test logger
	public static void testLogger(String className, String message) {
		TEST_LOGGER.debug(className + " - " + message, message);
	}
}
