package com.cti.npmeetup.util;

import java.util.Date;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

/**
 * Utility module for generating and validating JSON Web Tokens (JWTs), 
 * used to secure REST endpoints by enabling token-based authentication 
 * and authorization
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Component
public final class JwtUtil {
	private static final String TAG = JwtUtil.class.getSimpleName();
	//private static final String SECRET_KEY = "mysecretkey123";
	private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); 

	// ===================
	// Constructor
	// ===================
	
	public JwtUtil() {}

	// ===================
	// Private methods
	// ===================

	// Extract All Claims
	private Claims extractClaims(String token) {
		return Jwts.parser()
			.setSigningKey(SECRET_KEY)
			.parseClaimsJws(token)
			.getBody();
	}

	// Check Token Expiration
	private boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	// ===================
	// Public methods
	// ===================
	
	// Generate Token
	public String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24-hour expiration
			//.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.signWith(SECRET_KEY)
			.compact();
	}
	
	// Validate Token
	public boolean validateToken(String token, String username) {
		String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
	
	// Extract Username
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
}
