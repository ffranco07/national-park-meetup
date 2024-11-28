package com.cti.npmeetup.facade.impl;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cti.npmeetup.facade.LocationFacade;
import com.cti.npmeetup.service.LocationService;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.Location;
import com.cti.npmeetup.model.UserLocation;

/**
 * Location facade implementation module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Service
public class LocationFacadeImpl implements LocationFacade {

	private final LocationService locationService;
	
	@Autowired
	public LocationFacadeImpl(LocationService locationService) {
		this.locationService = locationService;
	}

	@Override
	public Long getLocationRepositoryCount() throws DataStoreException {
		return locationService.getLocationRepositoryCount();
	}
	
	@Override
	public Long getUserLocationRepositoryCount() throws DataStoreException {
		return locationService.getUserLocationRepositoryCount();
	}
	
	@Override
	public List<Location> findAllLocations() throws DataStoreException {
		return locationService.findAllLocations();
	}
	
	@Override
	public List<UserLocation> findAllUserLocations() throws DataStoreException {
		return locationService.findAllUserLocations();
	}
	
	@Override
	public Optional<UserLocation> findCurrentUserLocation(String mobileNumber) throws DataStoreException {
		return locationService.findCurrentUserLocation(mobileNumber);
	}
	
	@Override
	public List<Location> saveLocations(List<Location> locations) throws DataStoreException {
		return locationService.saveLocations(locations);
	}

	@Override
	public List<UserLocation> saveUserLocations(List<UserLocation> userLocations) throws DataStoreException {
		return locationService.saveUserLocations(userLocations);
	}
	
	@Override
	public void updateUserLocations(List<UserLocation> userLocations) throws DataStoreException {
		locationService.updateUserLocations(userLocations);
	}
}
