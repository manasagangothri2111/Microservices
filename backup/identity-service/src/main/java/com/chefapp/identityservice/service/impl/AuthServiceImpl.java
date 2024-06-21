package com.chefapp.identityservice.service.impl;


import com.chefapp.identityservice.dto.JwtAuthResponse;
import com.chefapp.identityservice.dto.SignUpRequest;
import com.chefapp.identityservice.dto.SigninRequest;
import com.chefapp.identityservice.entity.Role;
import com.chefapp.identityservice.entity.User;
import com.chefapp.identityservice.repository.UserRepository;
import com.chefapp.identityservice.service.AuthService;
import com.chefapp.identityservice.service.JwtService;
import com.chefapp.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;
	@Override
	public JwtAuthResponse signup(SignUpRequest req) {
		System.out.println("AuthServiceImpl SignUp");
		var user = new User();
		user.setFirstName(req.getFirstName());
		user.setLastName(req.getLastName());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.setEmail(req.getEmail());
		/*user.builder().firstName(req.getFirstName()).lastName(req.getLastName())
				.email(req.getEmail()).password(passwordEncoder.encode(req.getPassword()))
				.role(Role.USER).build();*/
		System.out.println("email"+user.getEmail());
		userRepository.save(user);
		var jwt=jwtService.generateToken(user.getEmail());
		return JwtAuthResponse.builder().token(jwt).build();
	}
	@Override
	public JwtAuthResponse signin(SigninRequest req) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword()));
		if(authentication.isAuthenticated()) {
			return JwtAuthResponse.builder().token(jwtService.generateToken(req.getEmail())).build();
		}else{
			throw new UsernameNotFoundException("invalid user request");
		}
		}
	}

	/*@Override
	public JwtAuthResponse validateToken(SigninRequest req, String header) {
		String userEmail= jwtService.extractUserName(header);
		UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
		if(jwtService.isTokenValid(header,userDetails)) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword()));
			var validuser = userRepository.findByEmail(req.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid Email and Password"));
			System.out.println("authService"+validuser);
			var jwt = jwtService.generateToken(validuser);
			return JwtAuthResponse.builder().token(jwt).build();
		}else{
			return JwtAuthResponse.builder().token("invalid token").build();
		}
	}*/
