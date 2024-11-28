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
import com.cti.npmeetup.service.impl.LocationServiceImpl;

import com.cti.npmeetup.repository.LocationRepository;
import com.cti.npmeetup.repository.UserLocationRepository;

import com.cti.npmeetup.model.*;
import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.LogUtil;

/**
 * Location Service implementation unit test module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public class LocationServiceImplTest {
	private static final String TAG = LocationServiceImplTest.class.getSimpleName();

	@Mock
	private LocationRepository locationRepository;

	@Mock
	private UserLocationRepository userLocationRepository;
	
	@InjectMocks
	private LocationServiceImpl locationService; 
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindAll() {
		// Arrange
		Location mockLocation1 = new Location("ACAD", "Arcadia", "Maine", 44.3876119, -68.2039123);
		Location mockLocation2 = new Location("AMSA", "American Samoa", "American Samoa", -14.2331268, -169.4760133);
		Location mockLocation3 = new Location("ARCH", "Arches", "Utah", 38.5719944, -109.4735066);
		
		List<Location> mockLocations = new ArrayList<>();
		mockLocations.add(mockLocation1);
		mockLocations.add(mockLocation2);
		mockLocations.add(mockLocation3);
		
		// Mock location respoitory findAll response with mockLocations object
		when(locationRepository.findAll()).thenReturn(mockLocations);

		// Act
		List<Location> result = null;
		try {
			result = locationService.findAllLocations();
    } 
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(result);

		// Verify interaction
		verify(locationRepository, times(1)).findAll();
	}
	
	@Test
	public void testSaveLocations() {
		// Arrange
		Location mockLocation1 = new Location("ACAD", "Arcadia", "Maine", 44.3876119, -68.2039123);
		Location mockLocation2 = new Location("AMSA", "American Samoa", "American Samoa", -14.2331268, -169.4760133);
		Location mockLocation3 = new Location("ARCH", "Arches", "Utah", 38.5719944, -109.4735066);
		
		List<Location> mockLocations = new ArrayList<>();
		mockLocations.add(mockLocation1);
		mockLocations.add(mockLocation2);
		mockLocations.add(mockLocation3);
		
		// Mock location respoitory saveAll response with mockLocations object
		when(locationRepository.saveAll(mockLocations)).thenReturn(mockLocations);
		
		// Act
		List<Location> result = null;
		try {
			result = locationService.saveLocations(mockLocations);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(result);

		// Verify interaction
		verify(locationRepository, times(1)).saveAll(mockLocations);
	}

	@Test
	public void testSaveUserLocations() {
		// Arrange
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
		
		// Mock user location respoitory saveAll response with mockUserLocations object
		when(userLocationRepository.saveAll(mockUserLocations)).thenReturn(mockUserLocations);
		
		// Act
		List<UserLocation> result = null;
		try {
			result = locationService.saveUserLocations(mockUserLocations);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(result);

		// Verify interaction
		verify(userLocationRepository, times(1)).saveAll(mockUserLocations);
	}

	@Test
	public void testFindCurrentUserLocation_UserLocationExists() {                         
		// Arrange
		String mobileNumber = "5555555530";
		User mockUser = new User(30L, "Francisco", "Test", "changeitpwd", "francisco@mail.com", "5555555530", UserStatus.ACTIVE);
		Location mockLocation = new Location("PINN", "Pinnacles", "California", 36.4808829, -121.1593104);
		UserLocation mockUserLocation = new UserLocation(30L, mockUser, mockLocation);
		
		// Mock user location respoitory findCurrentUserLocation response with mockUserLocation object
		when(userLocationRepository.findCurrentUserLocation(mobileNumber)).thenReturn(Optional.of(mockUserLocation));
		
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
		
		// Verify interaction
		verify(userLocationRepository, times(1)).findCurrentUserLocation(mobileNumber);
	}
	
	@Test
	public void testFindCurrentUserLocation_UserLocationNotFound() {
		// Arrange
		String mobileNumber = "5555551111";  // Non-existent mobile number
		
		// Mock user location repository findCurrentUserLocation response with empty optional object
		when(userLocationRepository.findCurrentUserLocation(mobileNumber)).thenReturn(Optional.empty());
		
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> locationService.findCurrentUserLocation(mobileNumber));
		
		// Assert
		assertEquals("User location not found with mobileNumber: " + mobileNumber, exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());

		// Verify interaction
		verify(userLocationRepository, times(1)).findCurrentUserLocation(mobileNumber);
	}
}
