package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Strategy;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy{

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
