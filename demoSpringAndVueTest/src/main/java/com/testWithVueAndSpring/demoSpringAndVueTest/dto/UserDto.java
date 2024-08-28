package com.testWithVueAndSpring.demoSpringAndVueTest.dto;

import com.testWithVueAndSpring.demoSpringAndVueTest.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String error;
    private String message;
    private int statusCode;
    private User.Gender gender;
    private String photo;
    private MultipartFile file;
    private User user;
    private List<User> userList;
    private boolean isActive;


}
