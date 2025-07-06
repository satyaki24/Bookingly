package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String email;
    private String password;
    private String name;
}
