package com.ats.service;

import com.ats.entity.User;
import com.ats.payload.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserByEmail(String email) throws Exception;
    UserDTO getUserById(Long id) throws Exception;
    List<UserDTO> getAllUsers();
}
