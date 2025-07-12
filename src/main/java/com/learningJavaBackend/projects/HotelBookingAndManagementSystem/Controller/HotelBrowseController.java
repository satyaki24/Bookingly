package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Controller;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.HotelInfoDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.HotelPriceDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.HotelSearchRequest;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service.HotelService;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        var page= inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
