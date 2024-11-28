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
import com.cti.npmeetup.service.impl.UserServiceImpl;
import com.cti.npmeetup.repository.UserRepository;

import com.cti.npmeetup.model.User;
import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.LogUtil;

/**
 * User Service implementation unit test module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public class UserServiceImplTest {
	private static final String TAG = UserServiceImplTest.class.getSimpleName();

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserServiceImpl userService; 
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindById_UserExists() {                         
		// Arrange
		Long userId = 1L;
		User mockUser = new User(userId, "Francisco", "Test", "changeitpwd", "francisco@mail.com", "5555555530", UserStatus.ACTIVE);
		
		// Mock user respoitory findByMobileNumber response with mockUser object
		when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
		
		// Act
		Optional<User> foundUser = null;
		try {
			foundUser = userService.findById(userId);
    } 
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
		assertTrue(foundUser.isPresent());
		assertEquals("Francisco", foundUser.get().getFirstName());
		
		// Verify interaction
		verify(userRepository, times(1)).findById(userId);
	}
	
	@Test
	public void testFindById_UserNotFound() {
		// Arrange
		Long userId = 999L;
		
		// Mock user repository findById response with empty optional object
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> userService.findById(userId));
		
		// Assert
		assertEquals("User not found with userId: " + userId, exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());

		// Verify interaction
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	public void testFindByEmail_UserExists() {
		// Arrange
		String email = "francisco@mail.com";
		User mockUser = new User(30L, "Francisco", "Test", "changeitpwd", email, "5555555530", UserStatus.ACTIVE);
		
		// Mock user respoitory findByEmail response with mockUser object
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
		
		// Act
		Optional<User> foundUser = null;
		try {
			foundUser = userService.findByEmail(email);
    } 
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
		assertTrue(foundUser.isPresent());
		assertEquals("Francisco", foundUser.get().getFirstName());

		// Verify interaction
		verify(userRepository, times(1)).findByEmail(email);
	}
	
  @Test
	public void testFindByEmail_UserNotFound() {
		// Arrange
		String email = "francisco@aol.com";  // Non-existent email
		
		// Mock user respository findByEmail response with empty optional object
		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> userService.findByEmail(email));
		
		// Assert
		assertEquals("User not found with email: " + email, exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
		
		// Verify interaction
		verify(userRepository, times(1)).findByEmail(email);
	}
	
	@Test
	public void testFindByMobileNumber_UserExists() {
		// Arrange
		String mobileNumber = "5555555530";
		User mockUser = new User(30L, "Francisco", "Test", "changeitpwd", "francisco@mail.com", mobileNumber, UserStatus.ACTIVE);
		
		// Mock user respoitory findByMobileNumber response with mockUser object
		when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(mockUser));
		
		// Act
		Optional<User> foundUser = null;
		try {
			foundUser = userService.findByMobileNumber(mobileNumber);
    } 
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
		assertTrue(foundUser.isPresent());
		assertEquals("Francisco", foundUser.get().getFirstName());

		// Verify interaction
		verify(userRepository, times(1)).findByMobileNumber(mobileNumber);
	}
	
  @Test
	public void testFindByMobileNumber_UserNotFound() {
		// Arrange
		String mobileNumber = "5555551111";  // Non-existent mobile number
		
		// Mock user respository findByMobileNumber response with empty optional object
		when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());
		
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> userService.findByMobileNumber(mobileNumber));
		
		// Assert
		assertEquals("User not found with mobileNumber: " + mobileNumber, exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
		
		// Verify interaction
		verify(userRepository, times(1)).findByMobileNumber(mobileNumber);
	}

	@Test
	public void testFindAll() {
		// Arrange
		// Mock user 1
		User mockUser1 = new User(1L, "Charlie", "Bryant", "changeitpwd", "bryant@mail.com", "5555555501", UserStatus.ACTIVE);
		
		// Mock user 2
		User mockUser2 = new User(2L, "Ruben", "Johnson", "changeitpwd", "johnson@mail.com", "5555555502", UserStatus.ACTIVE);

		// Mock user 3
		User mockUser3 = new User(3L, "Mike", "Presley", "changeitpwd", "presley@mail.com", "5555555503", UserStatus.ACTIVE);

		List<User> mockUsers = new ArrayList<>();
		mockUsers.add(mockUser1);
		mockUsers.add(mockUser2);
		
		// Mock user respoitory findAll response with mockUsers object
		when(userRepository.findAll()).thenReturn(mockUsers);

		// Act
		List<User> result = null;
		try {
			result = userService.findAllUsers();
    } 
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
		assertNotNull(result);

		// Verify interaction
		verify(userRepository, times(1)).findAll();
	}
	
	@Test
	public void testCreateUser() {
		// Arrange
		User mockUser = new User(null, "Jane", "Doe", "changeitpwd", "jane@mail.com", "2222222222", UserStatus.INACTIVE);
		User mockedSavedUser = new User(1L, "Jane", "Doe", "hashedpassword", "jane@mail.com", "2222222222", UserStatus.INACTIVE);
		
		// Mock user respository save response with mockedSavedUser object
		when(passwordEncoder.encode("changeitpwd")).thenReturn("hashedpassword");
		when(userRepository.save(mockUser)).thenReturn(mockedSavedUser);
		
		// Act
		User result = null;
		try {
			result = userService.createUser(mockUser);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
		assertNotNull(result.getId());
		assertEquals("hashedpassword", result.getPassword());

		// Verify interaction
		verify(userRepository, times(1)).save(mockUser);
	}

	@Test
	public void testUpdateUser_UserFound() throws DataStoreException {
    // Arrange
    String mobileNumber = "5555555530";
    User mockExistingUser = new User(1L, "Francisco", "Test", "changeitpwd", "francisco@mail.com", mobileNumber, UserStatus.ACTIVE);
    User mockUpdatedUser = new User(1L, "Francisco", "Updated", "changeitpwd", "newemail@mail.com", mobileNumber, UserStatus.INACTIVE);
		
    // Mock repository to return an existing user
    when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(mockExistingUser));
    when(userRepository.save(any(User.class))).thenReturn(mockExistingUser);

    // Act
    User result = null;
		
		try {
			result = userService.updateUser(mobileNumber, mockUpdatedUser);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
    assertNotNull(result);
    assertEquals("Francisco", result.getFirstName());  // Should remain the same
    assertEquals("Updated", result.getLastName());  // Should be updated
    assertEquals("newemail@mail.com", result.getEmail());  // Should be updated
		assertEquals("newemail@mail.com", result.getEmail());  // Should be updated
		assertEquals(UserStatus.INACTIVE, result.getUserStatus()); // Should be updated
		
    // Verify interactions
    verify(userRepository, times(1)).findByMobileNumber(mobileNumber);
    verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	public void testUpdateUser_UserNotFound() throws DataStoreException {
    // Arrange
		String mobileNumber = "5555551111";  // Non-existent mobile number
    User mockUpdatedUser = new User(null, "Francisco", "Updated", "newemail@mail.com", null, null, null);
		
    // Mock repository to return an empty Optional (user not found)
    when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

    // Act & Assert
    DataStoreException exception = assertThrows(DataStoreException.class, () -> userService.updateUser(mobileNumber, mockUpdatedUser));

    // Assert
    assertEquals("User not found with mobileNumber: " + mobileNumber, exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());

    // Verify interactions
    verify(userRepository, times(1)).findByMobileNumber(mobileNumber);
    verify(userRepository, times(0)).save(any(User.class));  // Save should not be called
	}
	
	@Test
	public void testDeleteUser_UserExists() {
		// Arrange
		String mobileNumber = "5555555530";

		// Mock user respository existsByMobileNumber response with true
		when(userRepository.existsByMobileNumber(mobileNumber)).thenReturn(true);
		//doNothing().when(userRepository).deleteByMobileNumber(mobileNumber);
		
		// Act
		boolean result = false;
		try {
			result = userService.deleteUser(mobileNumber);
		}
		catch (DataStoreException e) {
			LogUtil.testLogger(TAG, e.getMessage());
			fail("Expected no exception, but got: " + e.getMessage());
		}
		
		// Assert
		assertTrue(result);
		
		// Verify interaction
		verify(userRepository, times(1)).existsByMobileNumber(mobileNumber);
	}
	
	@Test
	public void testDeleteUser_UserNotFound() {
		// Arrange
		String mobileNumber = "1234567890";
		
		// Mock user respository existsByMobileNumber response with false
		when(userRepository.existsByMobileNumber(mobileNumber)).thenReturn(false);
	
		// Act & Assert
		DataStoreException exception = assertThrows(DataStoreException.class, () -> userService.deleteUser(mobileNumber));
		
		// Assert
		assertEquals("User not found with mobileNumber: " + mobileNumber, exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());

		// Verify interaction
		verify(userRepository, times(1)).existsByMobileNumber(mobileNumber);
	}
}
