package com.cti.npmeetup.service;

import java.util.Optional;
import java.util.List;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.UserLocation;

/**
 * Proximity service interface
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public interface ProximityService {
	// Queries
	public List<UserLocation> findNearbyUsers(String mobileNumber, double radiusKm) throws DataStoreException; 
	
}
