package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
