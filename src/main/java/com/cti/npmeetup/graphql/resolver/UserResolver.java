package com.cti.npmeetup.graphql.resolver;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import com.cti.npmeetup.exception.*;
import com.cti.npmeetup.facade.UserFacade;
import com.cti.npmeetup.model.*;

import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.LogUtil;

/**
 * GraphQL user resolver module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Controller
public class UserResolver {
	private static final String TAG = UserResolver.class.getSimpleName();

	@Autowired
	private UserFacade userFacade;

	// Query for fetching a user by mobile number
	@QueryMapping
	public User getUserByMobileNumber(@Argument 
																		String mobileNumber) throws DataStoreGraphQLException {
		User user = null;
		try {
			// DEBUG
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In getAllOrders");
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);
			user = userFacade.findUserByMobileNumber(mobileNumber).get();
		}
		catch (DataStoreException dse) {
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + dse.getMessage());
			throw new DataStoreGraphQLException("Unable to fetch data from the database. Please try again later.");
    } 
    catch (Exception e) {
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + e.getMessage());
			throw new DataStoreGraphQLException("Internal server error. Please contact support.");
    }
		return user;
	}
	
	// 	Mutation for adding a user
	@MutationMapping
	public User addUser(@Argument 
											String firstName,
											@Argument 
											String lastName,
											@Argument 
											String password,
											@Argument 
											String email,
											@Argument 
											String mobileNumber,
											@Argument 
											UserStatus userStatus) throws DataStoreGraphQLException {

		User user = null;
		User savedUser = null;
		try {
			// DEBUG
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In addUser");
			LogUtil.messageLogger(TAG, "firstName: " + firstName);
			LogUtil.messageLogger(TAG, "lastName: " + lastName);
			//LogUtil.messageLogger(TAG, "password: " + password);
			LogUtil.messageLogger(TAG, "email: " + email);
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);
			LogUtil.messageLogger(TAG, "userStatus: " + userStatus);
			
			user = ModelFactory.createUser(firstName, lastName, password, email, mobileNumber, userStatus);
			savedUser = userFacade.createUser(user);
		}
		catch (DataStoreException dse) {
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + dse.getMessage());
			throw new DataStoreGraphQLException("Unable to fetch data from the database. Please try again later.");
    } 
    catch (Exception e) {
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + e.getMessage());
			throw new DataStoreGraphQLException("Internal server error. Please contact support.");
    }
		return savedUser;
	}
	
	// Mutation for removing a user
	@MutationMapping
	public Boolean removeUser(@Argument 
														String mobileNumber) throws DataStoreGraphQLException {
		Boolean isRemoved = false;
		try {
			// DEBUG
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In removeUser");
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);
			isRemoved = userFacade.deleteUser(mobileNumber);
		}
		catch (DataStoreException dse) {
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + dse.getMessage());
			throw new DataStoreGraphQLException("Unable to fetch data from the database. Please try again later.");
    } 
    catch (Exception e) {
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + e.getMessage());
			throw new DataStoreGraphQLException("Internal server error. Please contact support.");
    }
		return isRemoved;
	}
}
	
