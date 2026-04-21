package com.ats.service;

import com.ats.payload.dto.UserDTO;
import com.ats.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signUp(UserDTO userDTO) throws Exception;
}
