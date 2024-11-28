package com.cti.npmeetup.service.impl;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.service.UserService;

import com.cti.npmeetup.repository.UserRepository;
import com.cti.npmeetup.model.User;

/**
 * User service implementation module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Long getUserRepositoryCount() throws DataStoreException {
		Long count = null;
		try {
			count = userRepository.count();
		}
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return count;
	}
	
	@Override
	public Optional<User> findById(Long userId) throws DataStoreException {
		Optional<User> user = null;
		try {
			user = userRepository.findById(userId);
			if (user.isEmpty()) {
				throw new DataStoreException("User not found with userId: " + userId, HttpStatus.NOT_FOUND);
			}
		} 
		catch (DataStoreException dse) {
			throw dse;
    }
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return user;
	}
	
	@Override
	public Optional<User> findByEmail(String email) throws DataStoreException {
		Optional<User> user = null;
		try {
			user = userRepository.findByEmail(email);
			if (user.isEmpty()) {
				throw new DataStoreException("User not found with email: " + email, HttpStatus.NOT_FOUND);
			}
		}
		catch (DataStoreException dse) {
			throw dse;
    }
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return user;
	}

	@Override
	public Optional<User> findByMobileNumber(String mobileNumber) throws DataStoreException {
		Optional<User> user = null;
		try {
			user = userRepository.findByMobileNumber(mobileNumber);
			if (user.isEmpty()) {
				throw new DataStoreException("User not found with mobileNumber: " + mobileNumber, HttpStatus.NOT_FOUND);
			}
		}
		catch (DataStoreException dse) {
			throw dse;
    }
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return user;
	}
	
	@Override
	public List<User> findAllUsers() throws DataStoreException {
		try {
			return userRepository.findAll();
		} 
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@Transactional
	public User createUser(User user) throws DataStoreException {
		try {
			// Encode plaintext password
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		} 
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@Transactional
	public User updateUser(String mobileNumber, User user) throws DataStoreException {
		Optional<User> existingUser = null;
		try {
			// Find the existing user by mobile number
			existingUser = userRepository.findByMobileNumber(mobileNumber);
			
			// If user exists, update the fields
			if (!existingUser.isEmpty()) {
				// Update only the fields that are present in the user payload
				if (user.getFirstName() != null) {
					existingUser.get().setFirstName(user.getFirstName());
				}
				
				if (user.getLastName() != null) {
					existingUser.get().setLastName(user.getLastName());
				}
				
				if (user.getEmail() != null) {
					existingUser.get().setEmail(user.getEmail());
				}

				if (user.getMobileNumber() != null) {
					existingUser.get().setMobileNumber(user.getMobileNumber());
				}

				if (user.getUserStatus() != null) {
					existingUser.get().setUserStatus(user.getUserStatus());
				}
				
				// Save the updated user
				return userRepository.save(existingUser.get());
			} 
			else {
				throw new DataStoreException("User not found with mobileNumber: " + mobileNumber, HttpStatus.NOT_FOUND);
			}
    }
		catch (DataStoreException dse) {
			throw dse;
    }
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	}
	
	@Override
	@Transactional
	public boolean deleteUser(String mobileNumber) throws DataStoreException {
		try {
			if (userRepository.existsByMobileNumber(mobileNumber)) {
				userRepository.deleteByMobileNumber(mobileNumber);
				return true;
			} 
			else {
				throw new DataStoreException("User not found with mobileNumber: " + mobileNumber, HttpStatus.NOT_FOUND);
			}
		} 
		catch (DataStoreException dse) {
			throw dse;
    }
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public List<User> saveUsers(List<User> users) throws DataStoreException {
		try {
			return userRepository.saveAll(users);
		} 
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}



