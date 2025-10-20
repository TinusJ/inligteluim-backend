package com.tinusj.inligteluim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinusj.inligteluim.dto.SignInRequest;
import com.tinusj.inligteluim.dto.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSignup() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("newuser", "newuser@example.com", "password123");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void testSignupWithInvalidData() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("ab", "invalid-email", "123");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSigninAfterSignup() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("signinuser", "signin@example.com", "password123");
        
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        SignInRequest signInRequest = new SignInRequest("signinuser", "password123");
        
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void testSigninWithInvalidCredentials() throws Exception {
        SignInRequest signInRequest = new SignInRequest("nonexistent", "wrongpassword");

        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().is4xxClientError());
    }
}
