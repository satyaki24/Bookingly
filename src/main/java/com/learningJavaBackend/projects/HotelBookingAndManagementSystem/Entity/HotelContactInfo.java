package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class HotelContactInfo {
    private String address;
    private String phoneNumber;
    private String location;
    private String email;
}
