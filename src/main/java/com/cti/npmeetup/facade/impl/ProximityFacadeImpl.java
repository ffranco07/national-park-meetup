package com.cti.npmeetup.facade.impl;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cti.npmeetup.facade.ProximityFacade;
import com.cti.npmeetup.service.ProximityService;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.model.UserLocation;

/**
 * Proximity facade implementation module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Service
public class ProximityFacadeImpl implements ProximityFacade {

	private final ProximityService proximityService;
	
	@Autowired
	public ProximityFacadeImpl(ProximityService proximityService) {
		this.proximityService = proximityService;
	}
	
	@Override
	public List<UserLocation> findNearbyUsers(String mobileNumber, double radiusKm) throws DataStoreException {
		return proximityService.findNearbyUsers(mobileNumber, radiusKm);
	}
}
