package com.cti.npmeetup.util;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cti.npmeetup.util.LogUtil;

/**
 * Filter module that intercepts requests and validates the access token
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Component
public final class JwtRequestFilter extends OncePerRequestFilter {
	private static final String TAG = JwtRequestFilter.class.getSimpleName();
	private final JwtUtil jwtUtil;
	
	public JwtRequestFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		// Skip processing for /auth/** paths
    String requestURI = request.getRequestURI();
    if (requestURI.startsWith("/auth/") || requestURI.startsWith("/h2-console") || requestURI.startsWith("/docs")) {
			chain.doFilter(request, response);  // Proceed without any further processing
			return;
    }
		
    final String authorizationHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

		// DEBUG
		LogUtil.messageLogger(TAG, "#########################");
		LogUtil.messageLogger(TAG, "In doFilterInternal");
		LogUtil.messageLogger(TAG, "authorizationHeader: " + authorizationHeader);
		
    // Check if Authorization header is present
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
    } 
		else {
			// If no Authorization header is provided, send a 401 Unauthorized response
			LogUtil.errorLogger(TAG, "No Authorization header found, sending 401 Unauthorized");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Explicitly set the status
			response.setContentType("application/json");  // Set content type to JSON
			response.getWriter().write("{\"error\": \"Authorization token is missing\"}"); // Return JSON response
			response.flushBuffer(); // Ensure the response is committed
			return;  // Exit the filter chain early
    }
		
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// If token is valid, authenticate the user
			if (jwtUtil.validateToken(jwt, username)) {
				UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
    }
		
    // Continue with the filter chain if token is valid, otherwise stop at response.sendError
    chain.doFilter(request, response);
	}
	
}
