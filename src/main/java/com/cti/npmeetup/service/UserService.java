package com.cti.npmeetup.service;

import java.util.Optional;
import java.util.List;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.User;

/**
 * User service interface
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public interface UserService {
	// Queries
	public Long getUserRepositoryCount() throws DataStoreException;
	public Optional<User> findById(Long userId) throws DataStoreException;
	public Optional<User> findByEmail(String email) throws DataStoreException;
	public Optional<User> findByMobileNumber(String mobileNumber) throws DataStoreException;
	public List<User> findAllUsers() throws DataStoreException;
	
	// Mutations
	public User createUser(User user) throws DataStoreException;
	public User updateUser(String mobileNumber, User user) throws DataStoreException;
	public boolean deleteUser(String mobileNumber) throws DataStoreException;
	public List<User> saveUsers(List<User> users) throws DataStoreException;
	
}
