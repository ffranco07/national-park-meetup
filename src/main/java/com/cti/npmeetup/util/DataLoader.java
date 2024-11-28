package com.cti.npmeetup.util;

import java.util.*;
import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cti.npmeetup.facade.UserFacade;
import com.cti.npmeetup.facade.LocationFacade;

import com.cti.npmeetup.model.*;
import com.cti.npmeetup.exception.ExcelReaderException;
import com.cti.npmeetup.util.LogUtil;

/**
 * Data loader component module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Component
public final class DataLoader implements CommandLineRunner {
	private static final String TAG = DataLoader.class.getSimpleName();

	private final PasswordEncoder passwordEncoder;
	private final ExcelReader reader;
	private final UserFacade userFacade;
	private final LocationFacade locationFacade;

	private List<User> savedUsers;
	private List<Location> savedLocations;
	
	// ===================
	// Constructor
	// ===================
	
	public DataLoader(PasswordEncoder passwordEncoder, ExcelReader reader, UserFacade userFacade, LocationFacade locationFacade) {
		this.passwordEncoder = passwordEncoder;
		this.reader = reader;
		this.userFacade = userFacade;
		this.locationFacade = locationFacade;
	}
	
	// ===================
	// Private methods
	// ===================
	
	private void loadUsers() {
		try {
			if (userFacade.getUserRepositoryCount() == 0) {
				List<User> newUsers = reader.readUsers();
				for (User newUser : newUsers) {
				// Encode plaintext password
					newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
				}
				savedUsers = userFacade.saveUsers(newUsers);
				// DEBUG
				LogUtil.messageLogger(TAG, "Users loaded successfully!");
			}
			else {
				LogUtil.messageLogger(TAG, "Data already exists, skipping users table initialization.");
			}
		}
		catch (ExcelReaderException ere) {
			LogUtil.errorLogger(TAG, "Excel reader exception: ", ere);
		}
		catch (Exception e) {
			LogUtil.errorLogger(TAG, "An unexpected error occurred: ", e);
		}
	}
	
	private void loadLocations() {
		try {
			if (locationFacade.getLocationRepositoryCount() == 0) {
				savedLocations = locationFacade.saveLocations(reader.readLocations());
				// DEBUG
				LogUtil.messageLogger(TAG, "Locations loaded successfully!");
			}
			else {
				LogUtil.messageLogger(TAG, "Data already exists, skipping location table initialization.");
			}
		}
		catch (ExcelReaderException ere) {
			LogUtil.errorLogger(TAG, "Excel reader exception: ", ere);
		}
		catch (Exception e) {
			LogUtil.errorLogger(TAG, "An unexpected error occurred: ", e);
		}
	}
	
	private void loadUserLocations() {
		try {
			if (locationFacade.getUserLocationRepositoryCount() == 0) {
				List<UserLocation> userLocations = ModelFactory.createUserLocations(savedUsers, savedLocations);
				locationFacade.saveUserLocations(userLocations);
				// DEBUG
				LogUtil.messageLogger(TAG, "User locations loaded successfully!");
			}
			else {
				LogUtil.messageLogger(TAG, "Data already exists, skipping user_location table initialization.");
			}
		}
		catch (Exception e) {
			LogUtil.errorLogger(TAG, "An unexpected error occurred: ", e);
		}
	}
	
	// ===================
	// Public methods
	// ===================
	
	@Override
	public void run(String... args) throws Exception {
		// Load users
		loadUsers();

		// Load locations
		loadLocations();

		// Load user locations
		loadUserLocations();
	}
}
