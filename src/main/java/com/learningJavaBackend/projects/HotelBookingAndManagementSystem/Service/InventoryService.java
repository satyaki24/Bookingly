package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}
