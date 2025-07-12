package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.DTO.*;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Hotel;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Inventory;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.Room;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception.ResourceNotFoundException;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository.HotelMinPriceRepository;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository.InventoryRepository;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    private final InventoryRepository inventoryRepository;

    private final HotelMinPriceRepository hotelMinPriceRepository;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today=LocalDate.now();
        LocalDate endDate=today.plusYears(1);

        for(; !today.isAfter(endDate); today=today.plusDays(1)){
            Inventory inventory=Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();

            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        log.info("Deleting the inventories of the room with id: {}", room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("Searching hotels for {} city from {} to {}",
                hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate());

        Pageable pageable= PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());

        long dateCount =
                ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate()) + 1;


        Page<HotelPriceDto> hotelPage= hotelMinPriceRepository.findHotelWithAvailableInventory(hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate(),
                hotelSearchRequest.getRoomsCount(), dateCount, pageable);

        return hotelPage;
    }

    @Override
    public List<InventoryDto> getAllInventoryByRoom(Long roomId) {
        log.info("Getting all inventories by room for room with Id: {}", roomId);
        Room room=roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with Id: "+roomId));

        User user=getCurrentUser();

        if(!user.equals(room.getHotel().getOwner())){
            throw new AccessDeniedException("Your are not the owner of the room with Id: "+roomId);
        }
        return inventoryRepository.findByRoomOrderByDate(room).stream()
                .map((element) -> modelMapper.map(element, InventoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto) {
        log.info("Updating all inventories by room for room with Id: {} between date range - {} to {}", roomId, updateInventoryRequestDto.getStartDate(), updateInventoryRequestDto.getEndDate());
        Room room=roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with Id: "+roomId));

        User user=getCurrentUser();

        if(!user.equals(room.getHotel().getOwner())){
            throw new AccessDeniedException("Your are not the owner of the room with Id: "+roomId);
        }

        inventoryRepository.getInventoryAndLockBeforeUpdate(roomId, updateInventoryRequestDto.getStartDate(), updateInventoryRequestDto.getEndDate());

        inventoryRepository.updateInventory(roomId, updateInventoryRequestDto.getStartDate(), updateInventoryRequestDto.getEndDate(), updateInventoryRequestDto.getClosed(), updateInventoryRequestDto.getSurgeFactor());
    }
}
