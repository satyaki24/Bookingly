package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Controller;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.BookingDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.HotelDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.HotelReportDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service.BookingService;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        log.info("Attempting to create a new Hotel with name: "+ hotelDto.getName());
        HotelDto hotel=hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId){
        log.info("Attempting to get Hotel with ID: "+ hotelId);
        HotelDto hotelDto=hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@RequestBody HotelDto hotelDto, @PathVariable Long hotelId){
        log.info("Attempting to Update Hotel with Id: " + hotelId);
        HotelDto hotelDto1=hotelService.updateHotelById(hotelDto, hotelId);
        return new ResponseEntity<>(hotelDto1, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId){
        log.info("Attempting to Delete Hotel of Id: "+ hotelId);
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{hotelId}/activate")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId){
        log.info("Attempting to Update Hotel of Id: "+ hotelId);
        hotelService.activateHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsByHotelId(@PathVariable Long hotelId){
        return ResponseEntity.ok(bookingService.getAllBookingsByHotelId(hotelId));
    }

    @GetMapping("/{hotelId}/reports")
    public ResponseEntity<HotelReportDto> getHotelReport(@PathVariable Long hotelId,
                                                         @RequestParam(required = false)LocalDate startDate,
                                                         @RequestParam(required = false)LocalDate endDate){

        if(startDate==null){
            startDate=LocalDate.now().minusMonths(1);
        }
        if(endDate==null){
            endDate=LocalDate.now();
        }

        return ResponseEntity.ok(bookingService.getHotelReport(hotelId, startDate, endDate));
    }
}
