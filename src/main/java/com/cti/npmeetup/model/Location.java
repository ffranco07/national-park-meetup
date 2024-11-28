package com.cti.npmeetup.model;

import java.time.ZonedDateTime;
import java.time.ZoneId;

import jakarta.persistence.*;

import com.cti.npmeetup.util.AppConstants;

/**
 * Location model
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Entity
@Table(name = "location")
public class Location {
	@Id
	@Column(name = "location_id")
	private String id;

	@Column(name = "park_name")
	private String parkName; // National park name

	@Column(name = "state")
	private String state; 

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	// ===================
	// Constructor(s)
	// ===================

	public Location() {}

	public Location(String id, String parkName, String state, Double latitude, Double longitude) {
		this.id = id;
		this.parkName = parkName;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	// ===================
	// Public methods
	// ===================

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Location location = (Location) o;
		
		return id.equals(location.id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "Location [id=" + id
			+ ", parkName=" + parkName
			+ ", state=" + state
			+ ", latitude=" + latitude
			+ ", longitude=" + longitude
			+ "]";
	}
}
