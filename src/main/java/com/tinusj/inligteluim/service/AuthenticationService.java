package com.tinusj.inligteluim.service;

import com.tinusj.inligteluim.domain.dto.JwtAuthenticationResponse;
import com.tinusj.inligteluim.domain.dto.SignInRequest;
import com.tinusj.inligteluim.domain.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}
