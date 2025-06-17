package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
