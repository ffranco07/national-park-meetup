package com.cti.npmeetup.integration;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.repository.LocationRepository;
import com.cti.npmeetup.repository.UserLocationRepository;
import com.cti.npmeetup.service.LocationService;

import com.cti.npmeetup.model.*;
import com.cti.npmeetup.util.LogUtil;

/**
 * Location Service functional test module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@SpringBootTest
@Transactional
@Rollback
public class LocationServiceFunctionalTest {
	private static final String TAG = LocationServiceFunctionalTest.class.getSimpleName();

	@Autowired
	private UserLocationRepository userLocationRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private LocationService locationService;
	
	@BeforeEach
	public void setUp() {
		// Load any test-specific configurations or seed data here
	}
	
	@Test
	public void testFindCurrentLocation() {
		// Arrange
		String mobileNumber = "5555555530";
		
		// Act
		Optional<UserLocation> foundUserLocation = null;
		
		try {
			foundUserLocation = locationService.findCurrentUserLocation(mobileNumber);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertTrue(foundUserLocation.isPresent());
		assertEquals("Francisco", foundUserLocation.get().getUser().getFirstName());
		assertEquals("PINN", foundUserLocation.get().getLocation().getId());
	}

	@Test
	public void testFindAllLocations() {
		// Act
		List<Location> foundLocations = null;
		
		try {
			foundLocations = locationService.findAllLocations();
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(foundLocations, "Location list should not be null");
		assertFalse(foundLocations.isEmpty(), "Location list should not be empty");
	}
	
	@Test
	public void testSaveLocations() {
		// Arrange
		Location newLocation = new Location("TEST", "Test Park", "Test State", 00.00, 00.00);
		List<Location> locations = new ArrayList<>();
		locations.add(newLocation);
		
		// Act
		List<Location> savedLocations = null;
		
		try {
			savedLocations = locationService.saveLocations(locations);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(savedLocations, "Location list should not be null");
		assertFalse(savedLocations.isEmpty(), "Location list should not be empty");
	}
}
