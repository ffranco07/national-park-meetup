package com.cti.npmeetup.util;

import java.util.Map;
import java.util.HashMap;

/**
 * Application constants module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public final class AppConstants {
	// Version id and date constants
	public static final String RELEASE_ID = "1.0.0";
	public static final String RELEASE_DATE = "11-13-2024"; // dd-MM-yyyy
	public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	public static final String TIME_ZONE = "America/Los_Angeles";

	// Logger constants
	public static final String MESSAGE_LOGGER = "com.cti.npmeetup.message";
	public static final String ERROR_LOGGER = "com.cti.npmeetup.error";
	public static final String SERVICE_LOGGER = "com.cti.npmeetup.service";
	public static final String TEST_LOGGER = "com.cti.npmeetup.test";

	// Excel files	
	public static final String USERS_FILE = "xlsx/Users.xlsx";
	public static final String NATIONAL_PARKS_FILE = "xlsx/National_Parks.xlsx";

	// Shared location index
	public static final Integer LOCATION_INDEX = 45;

	// Scheduled user location task flag
	public static final double STAGNATION_THRESHOLD = 0.9;
	
	// Shared user location map
	public static final Map<String, Integer> SHARED_USER_LOCATION_MAP;
	
	static {
		SHARED_USER_LOCATION_MAP = new HashMap<String, Integer>();
		SHARED_USER_LOCATION_MAP.put("Francisco", LOCATION_INDEX);
		SHARED_USER_LOCATION_MAP.put("Charlie", LOCATION_INDEX);
		SHARED_USER_LOCATION_MAP.put("Ruben", LOCATION_INDEX);
		SHARED_USER_LOCATION_MAP.put("Mike", LOCATION_INDEX);
	}
	
}
