package com.cti.npmeetup.model;

import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.AppConstants;
import com.cti.npmeetup.util.LogUtil;

/**
 * ModelFactory is a factory class used to create model objects
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public final class ModelFactory {
	private static final String TAG = ModelFactory.class.getSimpleName();
	private static final Map<String, Integer> SHARED_USER_LOCATION_MAP = AppConstants.SHARED_USER_LOCATION_MAP;
	
	// ====================================
	// Constructor
	// ====================================
    
	/**
	 * Creates a new ModelFactory object
	 */
	public ModelFactory() {}
	
	// ====================================
	// Private methods
	// ====================================


	// ====================================
	// Public methods
	// ====================================
	
	/**
	 *
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 * @param mobileNumber
	 * @param userStatus
	 * @return User
	 */
	public static User createUser(String firstName, String lastName, String password, String email, String mobileNumber, UserStatus userStatus) {
		// DEBUG
		LogUtil.messageLogger(TAG, "#########################");
		LogUtil.messageLogger(TAG, "In createUser");

		User user = new User(null, firstName, lastName, password, email, mobileNumber, userStatus);
		return user;
	}
	
	/**
	 *
	 * @param workbook
	 * @return List<User>
	 */
	public static List<User> createUsers(Workbook workbook) {
		// DEBUG
		LogUtil.messageLogger(TAG, "#########################");
		LogUtil.messageLogger(TAG, "In createUsers");
		
		Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet
			
		List<User> users = new ArrayList<>();
		User user = null;
		
		for (Row row : sheet) {
			if (row.getRowNum() == 0) continue; // Skip header row
			user = new User();
			// DEBUG
			//LogUtil.messageLogger(TAG, row.getCell(0).getStringCellValue());
			user.setFirstName(row.getCell(0).getStringCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, row.getCell(1).getStringCellValue());
			user.setLastName(row.getCell(1).getStringCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, row.getCell(2).getStringCellValue());
			user.setEmail(row.getCell(2).getStringCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, String.valueOf(row.getCell(3).getStringCellValue()));
			user.setPassword(row.getCell(3).getStringCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, String.valueOf(row.getCell(4).getStringCellValue()));
			user.setUserStatus(UserStatus.fromStatus(row.getCell(4).getStringCellValue()));
			
			// DEBUG
			//LogUtil.messageLogger(TAG, String.valueOf(row.getCell(5).getStringCellValue()));
			user.setMobileNumber(row.getCell(5).getStringCellValue().replaceAll("[^0-9]", ""));
			
			// DEBUG
			LogUtil.messageLogger(TAG, user.toString());
			users.add(user);
		}
		return users;
	}
	
	/**
	 *
	 * @param workbook
	 * @return List<Location>
	 */
	public static List<Location> createLocations(Workbook workbook) {
		// DEBUG
		LogUtil.messageLogger(TAG, "#########################");
		LogUtil.messageLogger(TAG, "In createLocations");
		
		Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet
			
		List<Location> locations = new ArrayList<>();
		Location location = null;
		
		for (Row row : sheet) {
			if (row.getRowNum() == 0) continue; // Skip header row
			location = new Location();
			// DEBUG
			//LogUtil.messageLogger(TAG, row.getCell(0).getStringCellValue());
			location.setParkName(row.getCell(0).getStringCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, row.getCell(1).getStringCellValue());
			location.setState(row.getCell(1).getStringCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, String.valueOf(row.getCell(2).getNumericCellValue()));
			location.setLatitude(row.getCell(2).getNumericCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, String.valueOf(row.getCell(3).getNumericCellValue()));
			location.setLongitude(row.getCell(3).getNumericCellValue());
			// DEBUG
			//LogUtil.messageLogger(TAG, row.getCell(4).getStringCellValue());
			location.setId(row.getCell(4).getStringCellValue()); // AlphaCode
			
			// DEBUG
			LogUtil.messageLogger(TAG, location.toString());
			locations.add(location);
		}
		return locations;
	}

	public static List<UserLocation> createUserLocations(List<User> users, List<Location> locations) {
    // DEBUG
    LogUtil.messageLogger(TAG, "#########################");
    LogUtil.messageLogger(TAG, "In createUserLocations");
    
    List<UserLocation> userLocations = new ArrayList<>();
		UserLocation userLocation = null;
    Random random = new Random();
		int locationIndex;
    for (User user : users) {
			userLocation = new UserLocation();
			
			// Set user from users list
			userLocation.setUser(user);
			
			// Check shared user location map to set location index
			if (SHARED_USER_LOCATION_MAP.containsKey(user.getFirstName())) {
				locationIndex = SHARED_USER_LOCATION_MAP.get(user.getFirstName());
			}
			else { 
				// Select a random location from the locations list
				locationIndex = random.nextInt(locations.size());
			}
			
			// Set location from locations list
			userLocation.setLocation(locations.get(locationIndex));
			
			// DEBUG
			LogUtil.messageLogger(TAG, userLocation.toString());
			userLocations.add(userLocation);
		}
		return userLocations;
	}
}
