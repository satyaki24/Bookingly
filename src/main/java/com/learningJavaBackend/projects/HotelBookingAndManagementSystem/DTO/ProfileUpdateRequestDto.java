package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
}
