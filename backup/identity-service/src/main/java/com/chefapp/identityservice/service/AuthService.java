package com.chefapp.identityservice.service;


import com.chefapp.identityservice.dto.JwtAuthResponse;
import com.chefapp.identityservice.dto.SignUpRequest;
import com.chefapp.identityservice.dto.SigninRequest;

public interface AuthService {
    JwtAuthResponse signup(SignUpRequest req);
    JwtAuthResponse signin(SigninRequest req);

  //  JwtAuthResponse validateToken(SigninRequest req, String header);


}
