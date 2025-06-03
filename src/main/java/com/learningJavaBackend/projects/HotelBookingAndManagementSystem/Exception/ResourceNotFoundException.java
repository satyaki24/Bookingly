package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
