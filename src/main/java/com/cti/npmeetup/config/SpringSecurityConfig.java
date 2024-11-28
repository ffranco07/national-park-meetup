package com.cti.npmeetup.config;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cti.npmeetup.util.JwtUtil;
import com.cti.npmeetup.util.JwtRequestFilter;

/**
 * Spring security configuration module
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 * Switches off Spring Boot automatic security configuration
 *
 * @author Francisco Franco
 * @version %I%, %G%
 * @since 1.0
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	private final JwtRequestFilter jwtRequestFilter;
	
	// ===================
	// Constructor
	// ===================
	
	public SpringSecurityConfig(JwtUtil jwtUtil) {
		this.jwtRequestFilter = new JwtRequestFilter(jwtUtil);
	}

	// ===================
	// Public methods
	// ===================
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // Disable CSRF
			.authorizeHttpRequests(auth -> auth
														 .requestMatchers("/auth/**", "/h2-console/**", "/docs/**").permitAll() // Allow unauthenticated access
														 .anyRequest().authenticated() // Secure all other endpoints
														 )
			.headers(headers -> 
							 headers.frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow H2 console in iframe
							 )
			.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
		
    return http.build();
	}
	
	// 	@Bean
	// 	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	// 		httpSecurity.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
	// 		httpSecurity.headers().frameOptions().disable();
	// 		return httpSecurity.build();
	// 	}
}
