package com.cti.npmeetup.integration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.repository.*;
import com.cti.npmeetup.service.ProximityService;

import com.cti.npmeetup.model.*;
import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.LogUtil;

/**
 * Proximity Service functional test module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@SpringBootTest
@Transactional
@Rollback
public class ProximityServiceFunctionalTest {
	private static final String TAG = ProximityServiceFunctionalTest.class.getSimpleName();

	@Autowired
	private ProximityService proximityService;
	
	@Autowired
	private UserLocationRepository userLocationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Test
	public void testFindNearbyUsers_UserLocationExists() {
		// Arrange
		String mobileNumber = "3333333330";
		double radiusKm = 5.0;
		
		// Create and save mock data
		User currMockUser = new User(null, "Mitch", "Von", "changeitpwd", "von@mail.com", mobileNumber, UserStatus.ACTIVE);
		userRepository.save(currMockUser);
		
		Location currMockLocation = new Location("YOSE", "Yosemite", "California", 37.8276596, -119.5053357);
		locationRepository.save(currMockLocation);
		
		UserLocation currMockUserLocation = new UserLocation(null, currMockUser, currMockLocation);
		userLocationRepository.save(currMockUserLocation);
		
		// Save other users and their locations
		User mockUser1 = new User(null, "Tom", "Jordan", "changeitpwd", "jordan@mail.com", "3333333302", UserStatus.ACTIVE);
		Location mockLocation1 = new Location("YOSE", "Yosemite", "California", 37.8276596, -119.5053357);
		userRepository.save(mockUser1);
		locationRepository.save(mockLocation1);
		userLocationRepository.save(new UserLocation(null, mockUser1, mockLocation1));
		
		User mockUser2 = new User(null, "Steve", "Gary", "changeitpwd", "gary@mail.com", "3333333303", UserStatus.ACTIVE);
		Location mockLocation2 = new Location("YOSE", "Yosemite", "California", 37.8276596, -119.5053357);
		userRepository.save(mockUser2);
		locationRepository.save(mockLocation2);
		userLocationRepository.save(new UserLocation(null, mockUser2, mockLocation2));
		
		// Act
		List<UserLocation> result = null;
		try {
			result = proximityService.findNearbyUsers(mobileNumber, radiusKm);
		} 
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
		assertNotNull(result);
		assertTrue(result.size() >= 2, "Expected at least 3 results, but got " + result.size());
	}
}

