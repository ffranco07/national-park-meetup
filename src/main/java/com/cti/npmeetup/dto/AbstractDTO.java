package com.cti.npmeetup.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * AbstractDTO is a super class extended by DTO objects 
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

public abstract class AbstractDTO implements Serializable {
	private String className = null;
	
	// ====================================
	// Constructor
	// ====================================
	
	public AbstractDTO() {}
	
	public AbstractDTO(String className) {
		this.className = className;
	}
	
	// ====================================
	// Public methods
	// ====================================
	
	public abstract String toString();

	@JsonIgnore
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
}
