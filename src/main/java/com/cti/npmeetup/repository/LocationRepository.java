package com.cti.npmeetup.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cti.npmeetup.model.Location;

/**
 * Location repository interface
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public interface LocationRepository extends JpaRepository<Location, Long> {
	public Optional<Location> findById(String id);
	public List<Location> findAll();
}
