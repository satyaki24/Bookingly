package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Util;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
