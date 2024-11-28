package com.cti.npmeetup.facade.impl;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cti.npmeetup.facade.UserFacade;
import com.cti.npmeetup.service.UserService;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.User;

/**
 * User facade implementation module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Service
public class UserFacadeImpl implements UserFacade {

	private final UserService userService;
	
	@Autowired
	public UserFacadeImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Long getUserRepositoryCount() throws DataStoreException {
		return userService.getUserRepositoryCount();
	}
	
	@Override
	public Optional<User> findUserById(Long userId) throws DataStoreException {
		return userService.findById(userId);
	}

	@Override
	public Optional<User> findUserByEmail(String email) throws DataStoreException {
		return userService.findByEmail(email);
	}
	
	@Override
	public Optional<User> findUserByMobileNumber(String mobileNumber) throws DataStoreException {
		return userService.findByMobileNumber(mobileNumber);
	}
	
	@Override
	public List<User> findAllUsers() throws DataStoreException {
		return userService.findAllUsers();
	}
	
	@Override
	public User createUser(User user) throws DataStoreException {
		return userService.createUser(user);
	}
	
	@Override
	public User updateUser(String mobileNumber, User user) throws DataStoreException {
		return userService.updateUser(mobileNumber, user);
	}
	
	@Override
	public boolean deleteUser(String mobileNumber) throws DataStoreException {
		return userService.deleteUser(mobileNumber);
	}
	
	@Override
	public  List<User> saveUsers(List<User> users) throws DataStoreException {
		return userService.saveUsers(users);
	}
}
