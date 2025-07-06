package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.BookingDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.BookingRequest;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.GuestDto;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.*;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.enums.BookingStatus;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception.ResourceNotFoundException;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception.UnAuthorisedException;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final GuestRepository guestRepository;
    private final ModelMapper modelMapper;

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {

        log.info("Initialising booking for hotel: {}, room: {} and date {}--{}",
                bookingRequest.getHotelId(), bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        Hotel hotel=hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(()->
                new ResourceNotFoundException("Hotel not found with Id: " + bookingRequest.getHotelId()));

        Room room=roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(()->
                new ResourceNotFoundException("Room not found with Id: " + bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),
                bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());

        long daysCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()) +1;

        if(inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore");
        }

//        Reserve the room / update the booked count of inventories

        for(Inventory inventory : inventoryList){
           inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

//        Creating the Booking

//        TODO: Calculate dynamic price

        Booking booking= Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking= bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {

        log.info("Adding guests for booking with Id: {}", bookingId);

        Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->
                new ResourceNotFoundException("Booking not found with Id: " + bookingId));

        User user=getCurrentUser();

        if(!user.equals(booking.getUser())){
            throw new UnAuthorisedException("Booking does not belong to this User with id: " + user.getId());
        }

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired ");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under Reserved Status, cannot add guests");
        }

        for(GuestDto guestDto : guestDtoList){
            Guest guest= modelMapper.map(guestDto, Guest.class);
            guest.setUser(user);
            guest= guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking=bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
       return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
