package com.cti.npmeetup.facade;

import java.util.Optional;
import java.util.List;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.Location;
import com.cti.npmeetup.model.UserLocation;

/**
 * Location facade (front-facing) interface
 *
 * Purpose: Finds the current location of a user
 * and other location related operations.
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public interface LocationFacade {
	// Queries
	public Long getLocationRepositoryCount() throws DataStoreException; 
	public Long getUserLocationRepositoryCount() throws DataStoreException; 
	public List<Location> findAllLocations() throws DataStoreException;
	public List<UserLocation> findAllUserLocations() throws DataStoreException;
	public Optional<UserLocation> findCurrentUserLocation(String mobileNumber) throws DataStoreException;
	
	// Mutations
	public List<Location> saveLocations(List<Location> locations) throws DataStoreException;
	public List<UserLocation> saveUserLocations(List<UserLocation> userLocations) throws DataStoreException;
	public void updateUserLocations(List<UserLocation> userLocations) throws DataStoreException; 
}