package com.ats.controller;

import com.ats.payload.dto.UserDTO;
import com.ats.payload.request.LoginRequest;
import com.ats.payload.response.AuthResponse;
import com.ats.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody UserDTO userDTO) throws Exception {
        AuthResponse authResponse = authService.signUp(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws Exception {
        AuthResponse response = authService.login(request.getEmail(),request.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
