package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.HotelContactInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceResponseDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private Double price;
}
