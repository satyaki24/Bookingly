package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Controller;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.RoomDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomAdminController {
    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "Create a new room in a hotel", tags = {"Admin Inventory"})
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        RoomDto room= roomService.createNewRoom(hotelId, roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all rooms in a hotel", tags = {"Admin Inventory"})
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId){
        return ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "Get a room by id", tags = {"Admin Inventory"})
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long HotelId, @PathVariable Long roomId){
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "Delete a room by id", tags = {"Admin Inventory"})
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long hotelId, @PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomId}")
    @Operation(summary = "Update a room", tags = {"Admin Inventory"})
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable Long hotelId, @PathVariable Long roomId,
                                                  @RequestBody RoomDto roomDto){

        return ResponseEntity.ok(roomService.updateRoomById(hotelId, roomId, roomDto));
    }
}
