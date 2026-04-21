package com.ats.service.impl;

import com.ats.entity.User;
import com.ats.mapper.UserMapper;
import com.ats.payload.dto.UserDTO;
import com.ats.repository.UserRepository;
import com.ats.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if(user == null){
            throw new Exception("User not found with email.");
        }

        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {

        User user = userRepo.findById(id).orElseThrow(() -> new Exception("User not found with ID :" + id));
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return UserMapper.toUserDtoList(users);
    }
}
