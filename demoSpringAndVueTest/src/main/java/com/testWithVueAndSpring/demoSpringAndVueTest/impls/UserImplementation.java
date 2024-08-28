package com.testWithVueAndSpring.demoSpringAndVueTest.impls;

import com.testWithVueAndSpring.demoSpringAndVueTest.dto.UserDto;
import com.testWithVueAndSpring.demoSpringAndVueTest.dto.UserLoginDto;
import com.testWithVueAndSpring.demoSpringAndVueTest.entities.User;
import com.testWithVueAndSpring.demoSpringAndVueTest.repositories.UserRepository;
import com.testWithVueAndSpring.demoSpringAndVueTest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImplementation implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto login(UserLoginDto dto) {
        UserDto userDto = new UserDto();
        try {
            User user = checkLoginUser(dto);
            if (user != null) {
                userDto.setUser(user);
                userDto.setStatusCode(200);
            } else {
                userDto.setStatusCode(404);
                userDto.setError("User not found");
            }
        } catch (Exception e) {
            userDto.setStatusCode(500);
            userDto.setError(e.getMessage());
        }
        return userDto;
    }

    @Override
    public UserDto save(UserDto dto) {
        var userDto = new UserDto();
        try {
            var user = sign_up(dto);
            userDto.setUser(user);
            userDto.setStatusCode(200);
        } catch (Exception e) {
            userDto.setStatusCode(500);
            userDto.setError(e.getMessage());
        }
        return userDto;
    }

    @Override
    public UserDto updatedUser(UserDto dto) {
        var userDto = new UserDto();
        try {
            var user = user_update(dto);
            if (user == null) {
                userDto.setStatusCode(404);
                userDto.setError("User Not found!");
            } else {
                userDto.setUser(user);
                userDto.setStatusCode(200);
                userDto.setMessage("Successfully updated!!");
            }
        } catch (Exception e) {
            userDto.setError(e.getMessage());
            userDto.setStatusCode(500);
        }

        return userDto;
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public UserDto deleteUser(Long id) {
        var dto = new UserDto();
        try {
            userRepository.deleteById(id);
            dto.setMessage("Successfully deleted user!!!");
            dto.setStatusCode(200);
        } catch (Exception e) {
            dto.setStatusCode(500);
            dto.setError(e.getMessage());
        }
        return dto;
    }

    @Override
    public UserDto findAll() {
        var dto = new UserDto();
        try {
            var users = userRepository.findAll();
            dto.setUserList(users);
            dto.setStatusCode(200);
        } catch (Exception e) {
            dto.setError(e.getMessage());
            dto.setStatusCode(500);
        }
        return dto;
    }

    @Override
    public UserDto changedProfile(String id, MultipartFile file) {
        var dto = new UserDto();
        try {
            var user = editedProfile(id, file);
            if (user == null) {
                dto.setStatusCode(404);
                dto.setError("User Not Found");
            } else {
                dto.setUser(user);
                dto.setStatusCode(200);
                dto.setMessage("Successfully Changed!");
            }
        } catch (Exception e) {
            dto.setError(e.getMessage());
            dto.setStatusCode(500);
        }
        return dto;
    }


    private User editedProfile(String id, MultipartFile file) {

        Long userId = Long.parseLong(id);
        userRepository.findById(userId).ifPresent(u -> {
            try {
                byte[] photoByte = file.getBytes();
                String encodedPhoto = Base64.getEncoder().encodeToString(photoByte);
                u.setPhoto(encodedPhoto);
                userRepository.save(u);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return userRepository.findById(userId).orElse(null);
    }

    private User user_update(UserDto dto) {
        userRepository.findById(dto.getId()).ifPresent(u -> {
            u.setName(dto.getName());
            u.setEmail(dto.getEmail());
            u.setPassword(dto.getPassword());
            u.setPhoneNumber(dto.getPhoneNumber());
            u.setGender(dto.getGender());
            userRepository.save(u);
        });
        return userRepository.findById(dto.getId()).orElse(null);
    }


    private User checkLoginUser(UserLoginDto dto) {
        return userRepository.findByEmail(dto.getEmail()).orElse(null);
    }

    private User sign_up(UserDto dto) {
        if (dto.getEmail() == null || dto.getEmail() == "") {
            throw new RuntimeException("Email cannot be empty!");
        }
        var user = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .gender(dto.getGender())
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

}
