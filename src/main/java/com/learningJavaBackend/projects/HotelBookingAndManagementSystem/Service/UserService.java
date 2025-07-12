package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.ProfileUpdateRequestDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.UserDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
