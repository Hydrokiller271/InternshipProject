package com.springboot.springlogin.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.springlogin.dto.UserDto;
import com.springboot.springlogin.model.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ConvertServiceImpl {
    @Autowired
    private ModelMapper modelMapper;

    public UserDto convertUserToDto(User users) {
        UserDto userDto = modelMapper.map(users, UserDto.class);
        return userDto;
    }
    public List<UserDto> convertUserListToDto(List<User> users){
        return users.stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());
    }
    public User convertUserDtoToEntity(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
