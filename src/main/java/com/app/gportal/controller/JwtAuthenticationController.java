package com.app.gportal.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.gportal.config.security.JwtRequest;
import com.app.gportal.config.security.JwtResponse;
import com.app.gportal.config.security.JwtTokenUtil;
import com.app.gportal.enums.ErrorCodeEnum;
import com.app.gportal.response.ErrorResponse;
import com.app.gportal.response.GPortalResponse;
import com.app.gportal.response.GPortalUsersResponseDTO;
import com.app.gportal.service.IGportalUserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private IGportalUserService userDetailsService;

	@PostMapping(value = "/login")
	public ResponseEntity<GPortalResponse<JwtResponse>> createAuthenticationToken(
			@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
			final GPortalUsersResponseDTO userDetails = (GPortalUsersResponseDTO) userDetailsService
					.loadUserByUsername(authenticationRequest.getUserName());
			final String token = jwtTokenUtil.generateToken(userDetails);
			JwtResponse jwtResponse = JwtResponse.builder().token(token).editAccess(true).build();
			return ResponseEntity.status(HttpStatus.OK).body(new GPortalResponse<JwtResponse>(true, null, jwtResponse));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			log.error("In method createAuthenticationToken. Password mismatch for username=" + authenticationRequest.getUserName(), e);
			ErrorResponse errorResponse = ErrorResponse.builder().errorCode(ErrorCodeEnum.GPORTAL_016.toString())
					.errorMessage(ErrorCodeEnum.GPORTAL_016.getDescription()).build();
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GPortalResponse<JwtResponse>(false, Arrays.asList(errorResponse), null));
		}
	}

	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}
