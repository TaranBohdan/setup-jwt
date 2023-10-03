package com.bohdan.JwtTokenGeneratorProj.controllers;

import com.bohdan.JwtTokenGeneratorProj.dtos.JwtRequest;
import com.bohdan.JwtTokenGeneratorProj.dtos.RegistrationUserDto;
import com.bohdan.JwtTokenGeneratorProj.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest)
    {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody RegistrationUserDto registrationUserDto)
    {
        return authService.createUser(registrationUserDto);
    }
}
