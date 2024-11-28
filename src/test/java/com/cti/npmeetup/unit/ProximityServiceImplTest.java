package com.cti.npmeetup.unit;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.service.impl.ProximityServiceImpl;
import com.cti.npmeetup.repository.UserLocationRepository;

import com.cti.npmeetup.model.*;
import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.LogUtil;

/**
 * Proximity Service implementation unit test module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public class ProximityServiceImplTest {
	private static final String TAG = ProximityServiceImplTest.class.getSimpleName();

	@Mock
	private UserLocationRepository userLocationRepository;
	
	@InjectMocks
	private ProximityServiceImpl proximityService; 
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindNearbyUsers_UserLocationExists() {                         
		// Arrange
		String mobileNumber = "5555555530";
		double radiusKm = 5.0;
		
		// Current Mock user
		User currMockUser = new User(30L, "Francisco", "Test", "changeitpwd", "francisco@mail.com", "5555555530", UserStatus.ACTIVE);
		Location currMockLocation = new Location("PINN", "Pinnacles", "California", 36.4808829, -121.1593104);
		UserLocation currMockUserLocation = new UserLocation(30L, currMockUser, currMockLocation);
		
		// Mock user 1
		User mockUser1 = new User(1L, "Charlie", "Bryant", "changeitpwd", "bryant@mail.com", "5555555501", UserStatus.ACTIVE);
		Location mockLocation1 = new Location("PINN", "Pinnacles", "California", 36.4808829, -121.1593104);
		UserLocation mockUserLocation1 = new UserLocation(1L, mockUser1, mockLocation1);
		
		// Mock user 2
		User mockUser2 = new User(2L, "Ruben", "Johnson", "changeitpwd", "johnson@mail.com", "5555555502", UserStatus.ACTIVE);
		Location mockLocation2 = new Location("PINN", "Pinnacles", "California", 36.4808829, -121.1593104);
		UserLocation mockUserLocation2 = new UserLocation(2L, mockUser2, mockLocation2);

		List<UserLocation> mockUserLocations = new ArrayList<>();
		mockUserLocations.add(mockUserLocation1);
		mockUserLocations.add(mockUserLocation2);
		
		// Mock user location respoitory findCurrentUserLocation response with currmockUserLocation object
		when(userLocationRepository.findCurrentUserLocation(mobileNumber)).thenReturn(Optional.of(currMockUserLocation));
		// Mock user location respoitory findAll response with mockUserLocations object
		when(userLocationRepository.findAll()).thenReturn(mockUserLocations);
		
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
		
		// Verify interaction
		verify(userLocationRepository, times(1)).findCurrentUserLocation(mobileNumber);
		verify(userLocationRepository, times(1)).findAll();
	}
	
	@Test
	public void testFindNearbyUsers_UserLocationNotFound() {
		// Arrange
		String mobileNumber = "5555551111";  // Non-existent mobile number
		double radiusKm = 5.0;
		
		// Mock user location repository findCurrentUserLocation response with empty optional object
		when(userLocationRepository.findCurrentUserLocation(mobileNumber)).thenReturn(Optional.empty());
		
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> proximityService.findNearbyUsers(mobileNumber, radiusKm));
		
		// Assert
		assertEquals("User location not found with mobileNumber: " + mobileNumber, exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());

		// Verify interaction
		verify(userLocationRepository, times(1)).findCurrentUserLocation(mobileNumber);
	}
}
