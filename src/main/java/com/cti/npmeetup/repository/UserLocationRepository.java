package com.cti.npmeetup.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param;

import com.cti.npmeetup.model.UserLocation;

/**
 * User location repository interface
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
	@Query("SELECT ul FROM UserLocation ul WHERE ul.user.mobileNumber = :mobileNumber ORDER BY ul.updatedAt DESC")
	Optional<UserLocation> findCurrentUserLocation(@Param("mobileNumber") String mobileNumber);
}
