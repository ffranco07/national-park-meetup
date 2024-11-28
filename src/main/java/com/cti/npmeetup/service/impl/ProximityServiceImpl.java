package com.cti.npmeetup.service.impl;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.service.ProximityService;

import com.cti.npmeetup.repository.UserLocationRepository;
import com.cti.npmeetup.model.UserLocation;
import com.cti.npmeetup.util.ProximityUtil;

/**
 * Proximity service implementation module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Service
public class ProximityServiceImpl implements ProximityService {
	private final UserLocationRepository userLocationRepository;
	
	@Autowired
	public ProximityServiceImpl(UserLocationRepository userLocationRepository) {
		this.userLocationRepository = userLocationRepository;
	}
	
	@Override
	public List<UserLocation> findNearbyUsers(String mobileNumber, double radiusKm) throws DataStoreException {
		try {
			Optional<UserLocation> currentUserLocation = userLocationRepository.findCurrentUserLocation(mobileNumber);
			
			if (currentUserLocation.isEmpty()) 
				throw new DataStoreException("User location not found with mobileNumber: " + mobileNumber, HttpStatus.NOT_FOUND);
			
			double currUserLat = currentUserLocation.get().getLocation().getLatitude();
			double currUserLon = currentUserLocation.get().getLocation().getLongitude();
			
			List<UserLocation> allUserLocations = userLocationRepository.findAll();
			return allUserLocations.stream()
				.filter(loc -> !loc.getUser().getMobileNumber().equals(mobileNumber)) // Exclude self
				.filter(loc -> ProximityUtil.calculateDistance(currUserLat, currUserLon, loc.getLocation().getLatitude(), loc.getLocation().getLongitude()) <= radiusKm)
				.collect(Collectors.toList());
		}
		catch (DataStoreException dse) {
			throw dse;
    }
		catch (Exception e) {
			throw new DataStoreException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}



