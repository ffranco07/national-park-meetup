package com.cti.npmeetup.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cti.npmeetup.util.JwtUtil;
import com.cti.npmeetup.util.LogUtil;

/**
 * Authentication controller module
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends AbstractController {
	private static final String TAG = AuthenticationController.class.getSimpleName();
	private final JwtUtil jwtUtil;
	
	@Autowired
	public AuthenticationController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/token")
	public String generateAccessToken(@RequestBody Map<String, String> 
																		request) {
		// DEBUG
		LogUtil.messageLogger(TAG, "#########################");
		LogUtil.messageLogger(TAG, "In generateAccessToken");
		String username = request.get("username"); // Retrieve the username
		// DEBUG
		LogUtil.messageLogger(TAG, "username: " + username);
		return jwtUtil.generateToken(username);
	}
}
