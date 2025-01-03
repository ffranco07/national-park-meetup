package com.cti.npmeetup.util;

/**
 * Proximity utility class used to calculate distance
 *
 * @author Francisco Franco
 * @author ChatGPT
 * @version %I%, %G%
 * @since 1.0
 */

public final class ProximityUtil {
	private static final int EARTH_RADIUS_KM = 6371;
	
	// Created this method with the help of ChatGPT
	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return EARTH_RADIUS_KM * c;
	}
}
