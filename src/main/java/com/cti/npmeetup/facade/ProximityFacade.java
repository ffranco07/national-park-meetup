package com.cti.npmeetup.facade;

import java.util.Optional;
import java.util.List;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.UserLocation;

/**
 * Proximity facade (front-facing) interface
 *
 * Purpose: Searches for user locations within a given
 * radius or bounding box
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public interface ProximityFacade {
	// Queries
	public List<UserLocation> findNearbyUsers(String mobileNumber, double radiusKm) throws DataStoreException;
	
}
