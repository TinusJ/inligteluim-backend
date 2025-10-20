package com.tinusj.inligteluim.service;

import com.tinusj.inligteluim.dto.JwtAuthenticationResponse;
import com.tinusj.inligteluim.dto.SignInRequest;
import com.tinusj.inligteluim.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}
