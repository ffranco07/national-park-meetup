package com.cti.npmeetup.enums;

/**
 * User status enumeration module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public enum UserStatus {
	ACTIVE ("ACTIVE"), // User is active
	INACTIVE("INACTIVE"), // User is inactive (ex. hasn't updated location in 1 hour)
	DISABLED("DISABLED"); // User is disabled
	
	private final String status;

	// ==================
	// Constructor
	// ==================

  private UserStatus(String status) {
    this.status = status;
  }
	
	// ==================
	// Public methods
	// ==================
	
  public String getStatus() {
		return status;
  }
	
	// Static method to get enum by status
	public static UserStatus fromStatus(String status) {
		for (UserStatus userStatus : UserStatus.values()) {
			if (userStatus.getStatus().equalsIgnoreCase(status)) {
				return userStatus;
			}
		}
		throw new IllegalArgumentException("No enum constant with status: " + status);
	}
}
