package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String message) {
        super(message);
    }
}
