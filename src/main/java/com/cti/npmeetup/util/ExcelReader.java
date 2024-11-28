package com.cti.npmeetup.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Component;

import com.cti.npmeetup.exception.ExcelReaderException;
import com.cti.npmeetup.model.*;

import com.cti.npmeetup.util.AppConstants;
import com.cti.npmeetup.util.LogUtil;

/**
 * Excel reader component module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Component
public final class ExcelReader {
	private static final String TAG = ExcelReader.class.getSimpleName();

	// ===================
	// Constructor
	// ===================
	
	public ExcelReader() {}

	// ===================
	// Public methods
	// ===================

	public List<User> readUsers() throws ExcelReaderException {
		List<User> users = null;
		InputStream is = null;
		Workbook workbook = null;
		try {
			is = getClass().getClassLoader().getResourceAsStream(AppConstants.USERS_FILE);
			workbook = new XSSFWorkbook(is);
			users = ModelFactory.createUsers(workbook);
		} 
		catch (Exception e) {
			throw new ExcelReaderException(e);
		}
		finally {
			try {
        if (workbook != null) {
					workbook.close();
        }
        if (is != null) {
					is.close();
        }
			} 
			catch (IOException ioe) {
				LogUtil.errorLogger(TAG, "An unexpected error occurred: ", ioe);
			}
			return users;
		}
	}
	
	public List<Location> readLocations() throws ExcelReaderException {
		List<Location> locations = null;
		InputStream is = null;
		Workbook workbook = null;
		try {
			is = getClass().getClassLoader().getResourceAsStream(AppConstants.NATIONAL_PARKS_FILE);
			workbook = new XSSFWorkbook(is);
			locations = ModelFactory.createLocations(workbook);
		} 
		catch (Exception e) {
			throw new ExcelReaderException(e);
		}
		finally {
			try {
        if (workbook != null) {
					workbook.close();
        }
        if (is != null) {
					is.close();
        }
			} 
			catch (IOException ioe) {
				LogUtil.errorLogger(TAG, "An unexpected error occurred: ", ioe);
			}
			return locations;
		}
	}
}
