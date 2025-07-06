package com.drip.competition.dto;

import com.drip.competition.entity.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String surname;
    private String patronymic;
    private String phoneNumber;
    private String email;
    private String hashedPassword;
    private boolean isAdmin;
    private LocalDateTime dateOfBirth;
    private Integer age;
    private Sex sex;
    private Float weight;
    private Float height;
    private LocalDateTime createdAt;
    private String bio;
    private String avatarUrl;
}
