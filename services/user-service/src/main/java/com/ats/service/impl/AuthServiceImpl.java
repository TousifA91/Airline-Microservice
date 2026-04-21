package com.ats.service.impl;

import com.ats.config.JwtProvider;
import com.ats.entity.User;
import com.ats.enums.UserRole;
import com.ats.mapper.UserMapper;
import com.ats.payload.dto.UserDTO;
import com.ats.payload.response.AuthResponse;
import com.ats.repository.UserRepository;
import com.ats.service.AuthService;
import com.ats.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponse signUp(UserDTO userDTO) throws Exception {
        User existingUser = userRepository.findByEmail(userDTO.getEmail());

        if(existingUser != null){
            throw new Exception("User with email already exists.");
        }

        if(userDTO.getRole().equals(UserRole.ROLE_SYSTEM_ADMIN)){
            throw new Exception("You cannot sign-up as System Admin");
        }

        User newUser = User.builder()
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .phone(userDTO.getPhone())
                .role(userDTO.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .lastLoggedIn(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),savedUser.getPassword()
        );

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toUserDto(savedUser));
        authResponse.setTitle("Welcome " + savedUser.getFullName());
        authResponse.setMessage("Registered Successfully ");
        return authResponse;
    }

    @Override
    public AuthResponse login(String email, String password) throws Exception {
        Authentication authentication = authenticate(email,password);

        User user = userRepository.findByEmail(email);
        user.setLastLoggedIn(LocalDateTime.now());
        userRepository.save(user);
        String jwt = jwtProvider.generateToken(authentication, user.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toUserDto(user));
        authResponse.setTitle("Welcome " + user.getFullName());
        authResponse.setMessage("Login Successfully ");

        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(!passwordEncoder.matches(
                password, userDetails.getPassword())){
            throw new Exception("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }
}
