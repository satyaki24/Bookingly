package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Strategy;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
