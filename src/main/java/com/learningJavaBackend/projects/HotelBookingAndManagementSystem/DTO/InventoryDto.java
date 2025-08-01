package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InventoryDto {
    private Long Id;
    private LocalDate date;
    private Integer bookedCount;
    private Integer reservedCount;
    private Integer totalCount;
    private BigDecimal surgeFactor;
    private BigDecimal price;
    private Boolean closed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
