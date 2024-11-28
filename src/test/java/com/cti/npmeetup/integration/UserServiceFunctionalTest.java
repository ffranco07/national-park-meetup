package com.cti.npmeetup.integration;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.repository.UserRepository;
import com.cti.npmeetup.service.UserService;

import com.cti.npmeetup.model.User;
import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.LogUtil;

/**
 * User Service functional test module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@SpringBootTest
@Transactional
@Rollback
public class UserServiceFunctionalTest {
	private static final String TAG = UserServiceFunctionalTest.class.getSimpleName();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Test
	public void testFindById_UserExists() throws DataStoreException {
		// Arrange
		User testUser = new User(null, "Max", "Henry", "changeitpwd", "henry@mail.com", "1111111101", UserStatus.ACTIVE);

		// Act
		User savedUser = null;
		User foundUser = null;
		try {
			savedUser = userRepository.save(testUser);
			foundUser = userService.findById(savedUser.getId()).get();
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(foundUser);
		assertEquals("Max", foundUser.getFirstName());
		assertEquals(savedUser.getId(), foundUser.getId());
	}
	
	@Test
	public void testFindById_UserNotFound() {
		// Act & Assert
		Long userId = 999L; // Non-existent ID
		DataStoreException exception = assertThrows(DataStoreException.class, () -> {
			userService.findById(userId);
		});
		
		assertEquals("User not found with userId: " + userId, exception.getMessage());
	}
	
	@Test
	public void testFindByMobileNumber_UserExists() throws DataStoreException {
		// Arrange
		String mobileNumber = "1111111102";
		User testUser = new User(null, "Justin", "Davis", "changeitpwd", "davis@mail.com", mobileNumber, UserStatus.ACTIVE);

		// Act
		User savedUser = null;
		User foundUser = null;
		try {
			savedUser = userRepository.save(testUser);
			foundUser = userService.findByMobileNumber(mobileNumber).get();
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(foundUser);
		assertEquals("Justin", foundUser.getFirstName());
		assertEquals(mobileNumber, foundUser.getMobileNumber());
		assertEquals(savedUser.getId(), foundUser.getId());
	}
	
	@Test
	public void testFindByMobileNumber_UserNotFound() {
		// Act & Assert
		String mobileNumber = "5555551111"; // Non-existent number
		DataStoreException exception = assertThrows(DataStoreException.class, () -> {
			userService.findByMobileNumber(mobileNumber);
		});
		
		assertEquals("User not found with mobileNumber: " + mobileNumber, exception.getMessage());
	}
	
	@Test
	public void testUpdateUser_UserExists() throws DataStoreException {
		// Arrange
		String mobileNumber = "1111111103";
		User originalUser = new User(null, "Shawn", "Flores", "changeitpwd", "flores@mail.com", mobileNumber, UserStatus.ACTIVE);
		User updatedUser = new User(null, "Shon", null, null, null, null, UserStatus.INACTIVE);
		
		// Act
		try {
			userRepository.save(originalUser);
			updatedUser = userService.updateUser(mobileNumber, updatedUser);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		assertNotNull(updatedUser);
		assertEquals("Shon", updatedUser.getFirstName());
		assertEquals(UserStatus.INACTIVE, updatedUser.getUserStatus());
	}
	
	@Test
	public void testUpdateUser_UserNotFound() {
		// Arrange
		String mobileNumber = "1111111104"; // Non-existent number
		User updatedUser = new User(null, "Frank", null, null, null, null, UserStatus.INACTIVE);
		
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> {
			userService.updateUser(mobileNumber, updatedUser);
		});
		
		assertEquals("User not found with mobileNumber: " + mobileNumber, exception.getMessage());
	}
	
	@Test
	public void testDeleteByMobileNumber_UserExists() {
		// Arrange
		String mobileNumber = "1111111105";
		User testUser = new User(null, "Jane", "Doe", "changeitpwd", "jane@mail.com", mobileNumber, UserStatus.INACTIVE);
		
		// Act
		try {
			userRepository.save(testUser);
			userService.deleteUser(mobileNumber);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
    }
		
		// Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> {
			userService.findByMobileNumber(mobileNumber);
		});
		
		assertEquals("User not found with mobileNumber: " + mobileNumber, exception.getMessage());
	}
	
	@Test
	public void testDeleteByMobileNumber_UserNotFound() {
		// Arrange
		String mobileNumber = "1111111106"; // Non-existent number
		
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> {
			userService.deleteUser(mobileNumber);
		});
		
		assertEquals("User not found with mobileNumber: " + mobileNumber, exception.getMessage());
	}
}
