package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private Long Id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
