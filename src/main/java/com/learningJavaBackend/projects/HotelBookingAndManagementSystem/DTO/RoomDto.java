package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDto {
    private Long Id;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;
}
