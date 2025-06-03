package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long HotelId, RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long HotelId);

    RoomDto getRoomById(Long RoomId);

    void deleteRoomById(Long RoomId);
}
