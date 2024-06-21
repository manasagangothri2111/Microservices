package com.chefapp.identityservice.controller;


import com.chefapp.identityservice.dto.JwtAuthResponse;
import com.chefapp.identityservice.dto.SignUpRequest;
import com.chefapp.identityservice.dto.SigninRequest;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthResponse> signUp(@RequestBody SignUpRequest req){
        System.out.println("SignUp");
        return ResponseEntity.ok(authService.signup(req));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signin(@RequestBody SigninRequest req){
        return ResponseEntity.ok(authService.signin(req));
    }
    @PostMapping("/token")
    public String getToken(@RequestBody SigninRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public void validateToken(@RequestParam("token") String token) {
        System.out.println("token>"+token);
        jwtService.validateToken(token);
        //return jwtService.isTokenValid(token,user);
    }


}
