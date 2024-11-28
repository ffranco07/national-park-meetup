package com.cti.npmeetup.facade;

import java.util.Optional;
import java.util.List;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.User;

/**
 * User facade (front-facing) interface
 *
 * Purpose: Handles user-related actions, such as
 * creating, updating, deleting, and retrieving users.
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public interface UserFacade {
	// Queries
	public Long getUserRepositoryCount() throws DataStoreException; 
	public Optional<User> findUserById(Long userId) throws DataStoreException;
	public Optional<User> findUserByEmail(String email) throws DataStoreException;
	public Optional<User> findUserByMobileNumber(String mobileNumber) throws DataStoreException;
	public List<User> findAllUsers() throws DataStoreException;
	
	// Mutations
	public User createUser(User user) throws DataStoreException;
	public User updateUser(String mobileNumber, User user) throws DataStoreException;
	public boolean deleteUser(String mobileNumber) throws DataStoreException;
	public  List<User> saveUsers(List<User> user) throws DataStoreException;
}
