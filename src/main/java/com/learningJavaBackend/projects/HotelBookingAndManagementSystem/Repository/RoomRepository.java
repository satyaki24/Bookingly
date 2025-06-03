package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
