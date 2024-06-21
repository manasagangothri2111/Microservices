package com.chefapp.identityservice.service;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserName(String tocken);
    String generateToken(String userName);

    boolean isTokenValid(String token, UserDetails userDetails);
    public void validateToken(final String token);
}
