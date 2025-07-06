package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.RoomDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Hotel;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Room;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception.ResourceNotFoundException;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception.UnAuthorisedException;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository.HotelRepository;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new Room in Hotel with Id: {}", hotelId);
        Hotel hotel= hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: " + hotelId ));

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own this hotel with Id: " + hotelId);
        }

        Room room=modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room=roomRepository.save(room);

        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all the Room in Hotel with Id: {}", hotelId);
        Hotel hotel= hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: " + hotelId ));

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own this hotel with Id: " + hotelId);
        }

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the Room with Id: {}", roomId);
        Room room= roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with ID: " + roomId ));

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the Room with Id: {}", roomId);
        Room room= roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with ID: " + roomId ));

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(room.getHotel().getOwner())){
            throw new UnAuthorisedException("This user does not own this room with Id: " + roomId);
        }

        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }
}
