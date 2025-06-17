package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelInfoDTO {
    private HotelDto hotels;
    private List<RoomDto> rooms;

}
