package com.cti.npmeetup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.facade.UserFacade;

import com.cti.npmeetup.model.User;
import com.cti.npmeetup.dto.ErrorDTO;
import com.cti.npmeetup.util.LogUtil;

/**
 * User controller module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractController {
	private static final String TAG = UserController.class.getSimpleName();
	private final UserFacade userFacade;
	
	@Autowired
	public UserController(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	// Endpoint to get user details by user's mobile number
	@GetMapping("/{mobileNumber}")
	public ResponseEntity<?> getUser(@PathVariable 
																	 String mobileNumber) {
		ErrorDTO error = null;
		try {
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In getUser");
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);
			
			// Remove any delimiters from mobile number 
			String plainMobileNumber = getPlainMobileNumber(mobileNumber);
			
			// Find user in the database using plain mobile number
			User user = userFacade.findUserByMobileNumber(plainMobileNumber).get();
			
			if (user != null) {
				// Ensure password not sent back in reponse
				user.setPassword(null);
			}
			
			// Return user and http status 200
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} 
		catch (DataStoreException dse) {
			error = createErrorDTO(dse.getMessage(), dse.getHttpStatus());
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + error.toString());
			// Return http status 
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    } 
		catch (Exception e) {
			error = createErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + error.toString());
			// Return http status 500
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    }
	}

	// Endpoint to get all users
	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		ErrorDTO error = null;
		try {
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In getAllUsers");
			
			// Find all users in the database
			List<User> users = userFacade.findAllUsers();

			if (users != null) {
				// Ensure password not sent back in reponse
				for (User user : users) {
					user.setPassword(null);
				}
			}
			
			// Return user and http status 200
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} 
		catch (DataStoreException dse) {
			error = createErrorDTO(dse.getMessage(), dse.getHttpStatus());
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + error.toString());
			// Return http status
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
		} 
		catch (Exception e) {
			error = createErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + error.toString());
			// Return http status 500
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
		}
	}
	
	// Endpoint to create a new user
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody 
																				 User user) {
		ErrorDTO error = null;
		try {
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In createUser");
			LogUtil.messageLogger(TAG, "user: " + user);
			
			// Remove any delimiters from mobile number in user object 
			setUserPlainMobileNumber(user);
			
			// Create the user in the database
			User createdUser = userFacade.createUser(user);
			
			if (user != null) {
				// Ensure password not sent back in reponse
				user.setPassword(null);
			}
			
			// Return created user and http status 201
			return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
		}
		catch (DataStoreException dse) {
			error = createErrorDTO(dse.getMessage(), dse.getHttpStatus());
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + error.toString());
			// Return http status 
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    } 
		catch (Exception e) {
			error = createErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + error.toString());
			// Return http status 500
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    }
	}
	
	// Endpoint to update an existing user
	@PutMapping("/{mobileNumber}")
	public ResponseEntity<?> updateUser(@PathVariable 
																				 String mobileNumber, 
																				 @RequestBody 
																				 User user) {
		ErrorDTO error = null;
		try {
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In updateUser");
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);
			LogUtil.messageLogger(TAG, "user: " + user);
			
			// Remove any delimiters from mobile number 
			String plainMobileNumber = getPlainMobileNumber(mobileNumber);
			
			// Remove any delimiters from mobile number in user object 
			setUserPlainMobileNumber(user);
			
			// Update user in the database using plain mobile number
			User updatedUser = userFacade.updateUser(plainMobileNumber, user);
			
			if (updatedUser != null) {
				// Ensure password not sent back in reponse
				updatedUser.setPassword(null);
			}
			
			// Return updated user and http status 200
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK); 
		}
		catch (DataStoreException dse) {
			error = createErrorDTO(dse.getMessage(), dse.getHttpStatus());
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + error.toString());
			// Return http status 
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    } 
		catch (Exception e) {
			error = createErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + error.toString());
			// Return http status 500
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    }
	}
	
	// Endpoint to delete a user
	@DeleteMapping("/{mobileNumber}")
	public ResponseEntity<?> deleteUser(@PathVariable 
																						String mobileNumber) {
		ErrorDTO error = null;
		try {
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In deleteUser");
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);

			// Remove any delimiters from mobile number 
			String plainMobileNumber = getPlainMobileNumber(mobileNumber);
			
			// Delete user in the database using plain mobile number
			boolean isDeleted = userFacade.deleteUser(plainMobileNumber);
			
			// Return HTTP 200 with the Boolean response (true/false)
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK); 
		}
		catch (DataStoreException dse) {
			error = createErrorDTO(dse.getMessage(), dse.getHttpStatus());
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + error.toString());
			// Return http status 
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    } 
		catch (Exception e) {
			error = createErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + error.toString());
			// Return http status 500
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    }
	}
}
