package com.cti.npmeetup.service.impl;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.service.LocationService;

import com.cti.npmeetup.repository.LocationRepository;
import com.cti.npmeetup.repository.*;

import com.cti.npmeetup.model.*;
import com.cti.npmeetup.util.LogUtil;

/**
 * Location service implementation module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Service
public class LocationServiceImpl implements LocationService {
	private static final String TAG = LocationServiceImpl.class.getSimpleName();

	private final UserRepository userRepository;
	private final LocationRepository locationRepository;
	private final UserLocationRepository userLocationRepository;
	
	@Autowired
	public LocationServiceImpl(UserRepository userRepository, LocationRepository locationRepository, UserLocationRepository userLocationRepository) {
		this.userRepository = userRepository;
		this.locationRepository = locationRepository;
		this.userLocationRepository = userLocationRepository;
	}

	@Override
	public Long getLocationRepositoryCount() throws DataStoreException {
		Long count = null;
		try {
			count = locationRepository.count();
		}
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return count;
	}

	@Override
	public Long getUserLocationRepositoryCount() throws DataStoreException {
		Long count = null;
		try {
			count = userLocationRepository.count();
		}
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return count;
	}
	
	@Override
	public List<Location> findAllLocations() throws DataStoreException {
		try {
			return locationRepository.findAll();
		} 
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<UserLocation> findAllUserLocations() throws DataStoreException {
		try {
			return userLocationRepository.findAll();
		} 
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public Optional<UserLocation> findCurrentUserLocation(String mobileNumber) throws DataStoreException {
		Optional<UserLocation> userLocation = null;
		try {
			userLocation = userLocationRepository.findCurrentUserLocation(mobileNumber);
			if (userLocation.isEmpty()) {
				throw new DataStoreException("User location not found with mobileNumber: " + mobileNumber, HttpStatus.NOT_FOUND);
			}
		}
		catch (DataStoreException dse) {
			throw dse;
    }
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return userLocation;
	}

	@Override
	@Transactional
	public List<Location> saveLocations(List<Location> locations) throws DataStoreException {
		try {
			return locationRepository.saveAll(locations);
		} 
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public List<UserLocation> saveUserLocations(List<UserLocation> userLocations) throws DataStoreException {
		try {
			return userLocationRepository.saveAll(userLocations);
		}
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@Transactional
	public void updateUserLocations(List<UserLocation> userLocations) throws DataStoreException {
		try {
			// DEBUG
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In updateUserLocations");

			// Explicitly save the user location
			for (UserLocation userLocation : userLocations) {
				userLocationRepository.save(userLocation);
				
				// Explicitly save the user
				User user = userLocation.getUser();
				// DEBUG
				LogUtil.messageLogger(TAG, "User status: " + user.getUserStatus().toString());
				userRepository.save(user);
			}
		} 
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}



