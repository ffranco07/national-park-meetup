package com.cti.npmeetup.service.impl;

import java.util.List;
import java.util.Random;

import java.time.ZonedDateTime;
import java.time.ZoneId;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cti.npmeetup.exception.DataStoreException;
import com.cti.npmeetup.service.LocationService;

import com.cti.npmeetup.model.*;
import com.cti.npmeetup.enums.UserStatus;
import com.cti.npmeetup.util.*;

/**
 * Schedules changes in user locations
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Service
public class ScheduledUserLocationTask {
	private static final String TAG = ScheduledUserLocationTask.class.getSimpleName();

	private final LocationService locationService;
	
	public ScheduledUserLocationTask(LocationService locationService) {
		this.locationService = locationService;
	}
	
	@Scheduled(fixedRate = 300000)  // Run every 5 minutes
	public void sendPeriodicUpdate() {
		List<Location> locations = null;
		List<UserLocation> userLocations = null;
		ZonedDateTime currentTime;
		ZonedDateTime lastUpdatedAt;
		Random random = new Random();
		int randomLocationIndex;
		Location randomLocation;
		try {
			// Fetch all locations and user locations
			locations = locationService.findAllLocations();
			userLocations = locationService.findAllUserLocations();
			
			// Get the current time to compare with the 
			// updatedAt timestamp for inactivity
			currentTime = ZonedDateTime.now();

			// Iterate over each user location
			for (UserLocation userLocation : userLocations) {
				// Get the updatedAt timestamp to check how long
				// the user has stayed at the current location
				lastUpdatedAt = userLocation.getUpdatedAt();
				
				// If the user has been at the same location for more than 1 hour 
				// (1 hour = 60 minutes = 3600 seconds)
				if (lastUpdatedAt != null && lastUpdatedAt.plusHours(1).isBefore(currentTime)) {
					// Set the user status to INACTIVE 
					// if they have been at the same location for over 1 hour
					userLocation.getUser().setUserStatus(UserStatus.INACTIVE);
					LogUtil.messageLogger(TAG, "User: " + userLocation.getUser().getFirstName() + " " + userLocation.getUser().getLastName() + " marked as INACTIVE due to inactivity.");
				}
				else {
					switch (userLocation.getUser().getUserStatus()) {
					case ACTIVE:
						// Set random location based on random index in locations list
						randomLocationIndex = random.nextInt(locations.size());
						randomLocation = locations.get(randomLocationIndex);
						
						// Use stagnation threshold to keep user at same location or move to new location
						// Higher value for stagnation threshold will keep user at same location
						// Lower value for stagnation threshold have the user moving more often
						if (random.nextDouble() < AppConstants.STAGNATION_THRESHOLD) {
							LogUtil.messageLogger(TAG, "User " + userLocation.getUser().getFirstName() + " " + userLocation.getUser().getLastName()  + " remained at the same location.");
						}
						else {
							// Update user location to random location
							userLocation.setLocation(randomLocation);
							// Set user location updatedAt
							userLocation.setUpdatedAt(ZonedDateTime.now(ZoneId.of(AppConstants.TIME_ZONE)));
							LogUtil.messageLogger(TAG, "User: " + userLocation.getUser().getFirstName() + " " + userLocation.getUser().getLastName() + " location updated to " + randomLocation.getId());
						}
					default:
						break;
					}
				}
			}
			
			// Persist the updated user location information
			locationService.updateUserLocations(userLocations);
    } 
		catch (DataStoreException dse) {
			// Log data store failure
			LogUtil.errorLogger(TAG, "Data store failure: ", dse);
		} 
		catch (Exception e) {
			// Log any other unexpected errors
			LogUtil.errorLogger(TAG, "An unexpected error occurred: ", e);
		}
	}
}
