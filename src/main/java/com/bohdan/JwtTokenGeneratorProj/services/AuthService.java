package com.bohdan.JwtTokenGeneratorProj.services;

import com.bohdan.JwtTokenGeneratorProj.dtos.JwtRequest;
import com.bohdan.JwtTokenGeneratorProj.dtos.JwtResponse;
import com.bohdan.JwtTokenGeneratorProj.dtos.RegistrationUserDto;
import com.bohdan.JwtTokenGeneratorProj.dtos.UserDto;
import com.bohdan.JwtTokenGeneratorProj.entities.User;
import com.bohdan.JwtTokenGeneratorProj.exceptions.AppError;
import com.bohdan.JwtTokenGeneratorProj.utilities.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest)
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }
        catch (BadCredentialsException e)
        {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody RegistrationUserDto registrationUserDto)
    {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword()))
        {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords aren't similar"), HttpStatus.BAD_REQUEST);
        }

        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent())
        {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "User is exist yet"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
