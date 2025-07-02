package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Strategy;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapped.calculatePrice(inventory);
        boolean isTodayHoliday=true;
        // TODO: call and API or check with local data. maybe future me add kar dunga. lets see.
        if(isTodayHoliday){
            price=price.multiply(BigDecimal.valueOf(1.15));
        }
        return price;
    }
}
