package com.cti.npmeetup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.facade.LocationFacade;

import com.cti.npmeetup.model.Location;
import com.cti.npmeetup.model.UserLocation;

import com.cti.npmeetup.dto.ErrorDTO;
import com.cti.npmeetup.util.LogUtil;

/**
 * Location controller module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@RestController
@RequestMapping("/api/location")
public class LocationController extends AbstractController {
	private static final String TAG = LocationController.class.getSimpleName();
	private final LocationFacade locationFacade;
	
	@Autowired
	public LocationController(LocationFacade locationFacade) {
		this.locationFacade = locationFacade;
	}

	@GetMapping("/current")
	public ResponseEntity<?> getCurrentUserLocation(@RequestParam 
																									String mobileNumber) {
		ErrorDTO error = null;
		try {
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In getCurrentUserLocation");
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);
			
			String plainMobileNumber = getPlainMobileNumber(mobileNumber);
			UserLocation userLocation = locationFacade.findCurrentUserLocation(plainMobileNumber).get();
			if (userLocation != null && userLocation.getUser() != null) {
				// Ensure password not sent back in reponse
				userLocation.getUser().setPassword(null);
			}
			
			return new ResponseEntity<UserLocation>(userLocation, HttpStatus.OK);
		}
		catch (DataStoreException dse) {
			error = createErrorDTO(dse.getMessage(), dse.getHttpStatus());
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: " + error.toString());
			// Return http status 
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    } 
		catch (Exception e) {
			error = createErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: " + error.toString());
			// Return http status 500
			return new ResponseEntity<ErrorDTO>(error, error.getHttpStatus());
    }
	}
}
