package com.testWithVueAndSpring.demoSpringAndVueTest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private String photo;
    private boolean isActive;



    public enum Gender{
        MALE,FEMALE,UNDEFINED
    }
}
