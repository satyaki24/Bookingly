package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Guest;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
  List<Guest> findByUser(User user);
  }