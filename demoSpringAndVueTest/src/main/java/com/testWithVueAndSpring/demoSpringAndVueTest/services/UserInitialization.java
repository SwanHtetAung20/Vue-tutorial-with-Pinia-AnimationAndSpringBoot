package com.testWithVueAndSpring.demoSpringAndVueTest.services;

import com.testWithVueAndSpring.demoSpringAndVueTest.entities.User;
import com.testWithVueAndSpring.demoSpringAndVueTest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInitialization {

    private final UserRepository userRepository;


    @Transactional
    @EventListener(classes = ContextClosedEvent.class)
    public void initializationUser() {
        System.out.println("Just Start");
        if (userRepository.count() == 0) {
            var user = new User();
            user.setName("ADMIN");
            user.setEmail("admin@gmail.com");
            user.setPassword("admin@123");
            user.setPhoneNumber("09987667551");
            user.setGender(User.Gender.MALE);
            user.setActive(true);
            userRepository.save(user);
            System.out.println("Just End");
        }
    }
}
