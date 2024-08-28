package com.testWithVueAndSpring.demoSpringAndVueTest.services;

import com.testWithVueAndSpring.demoSpringAndVueTest.dto.UserDto;
import com.testWithVueAndSpring.demoSpringAndVueTest.dto.UserLoginDto;
import com.testWithVueAndSpring.demoSpringAndVueTest.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public UserDto login(UserLoginDto dto);

    public UserDto save(UserDto dto);

    public UserDto updatedUser(UserDto dto);

    public UserDto findById(Long id);

    public UserDto deleteUser(Long id);

    public UserDto findAll();

    public UserDto changedProfile(String id, MultipartFile file);
}
