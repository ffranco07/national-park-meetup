package com.cti.npmeetup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.facade.ProximityFacade;

import com.cti.npmeetup.model.UserLocation;
import com.cti.npmeetup.dto.ErrorDTO;
import com.cti.npmeetup.util.LogUtil;

/**
 * Proximity controller module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@RestController
@RequestMapping("/api/proximity")
public class ProximityController extends AbstractController {
	private static final String TAG = ProximityController.class.getSimpleName();
	private final ProximityFacade proximityFacade;
	
	@Autowired
	public ProximityController(ProximityFacade proximityFacade) {
		this.proximityFacade = proximityFacade;
	}

	@GetMapping("/nearby")
	public ResponseEntity<?> getNearByUsers(@RequestParam 
																					String mobileNumber, 
																					@RequestParam(defaultValue = "5") 
																					double radiusKm) {
		ErrorDTO error = null;
		try {
			LogUtil.messageLogger(TAG, "#########################");
			LogUtil.messageLogger(TAG, "In getNearByUsers");
			LogUtil.messageLogger(TAG, "mobileNumber: " + mobileNumber);
			
			String plainMobileNumber = getPlainMobileNumber(mobileNumber);
			List<UserLocation> nearbyUsers = proximityFacade.findNearbyUsers(plainMobileNumber, radiusKm);
			
			if (nearbyUsers != null && !nearbyUsers.isEmpty()) {
				for (UserLocation userLocation : nearbyUsers) {
					if (userLocation != null && userLocation.getUser() != null) {
						// Ensure password not sent back in reponse
						userLocation.getUser().setPassword(null);
					}
				}
			}

			return new ResponseEntity<List<UserLocation>>(nearbyUsers, HttpStatus.OK);
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
