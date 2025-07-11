package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomPriceDto {
    private Room room;
    private Double price;
}
