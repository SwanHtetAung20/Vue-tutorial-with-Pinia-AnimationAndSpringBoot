package com.testWithVueAndSpring.demoSpringAndVueTest.controllers;


import com.testWithVueAndSpring.demoSpringAndVueTest.dto.UserDto;
import com.testWithVueAndSpring.demoSpringAndVueTest.dto.UserLoginDto;
import com.testWithVueAndSpring.demoSpringAndVueTest.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserLoginDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(dto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<UserDto> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deletedUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updatedUser(@RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatedUser(dto));
    }

    @PutMapping("/change-profile")
    public ResponseEntity<UserDto> changedProfile(@RequestParam String id, @RequestParam MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(userService.changedProfile(id,file));
    }
}
