package com.ats.mapper;

import com.ats.entity.User;
import com.ats.payload.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toUserDto(User user){
        if(user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .phone(user.getPhone())
                .lastLogin(user.getLastLoggedIn())
                .build();
    }

    public static List<UserDTO> toUserDtoList(List<User> users){
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
