package jtw.nice.service.impl;

import jtw.nice.entity.RoomCharge;
import jtw.nice.entity.dto.RoomBookingRequestDTO;
import jtw.nice.mapper.RoomChargeMapper;
import jtw.nice.mapper.RoomStatisticsMapper;
import jtw.nice.mapper.StateroomMapper;
import jtw.nice.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomBookingServiceImpl implements RoomBookingService {

    private final StateroomMapper stateroomMapper;
    private final RoomChargeMapper roomChargeMapper;
    private final RoomStatisticsMapper roomStatisticsMapper;

    @Autowired
    public RoomBookingServiceImpl(StateroomMapper stateroomMapper,
                                  RoomChargeMapper roomChargeMapper,
                                  RoomStatisticsMapper roomStatisticsMapper) {
        this.stateroomMapper = stateroomMapper;
        this.roomChargeMapper = roomChargeMapper;
        this.roomStatisticsMapper = roomStatisticsMapper;
    }

    @Override
    @Transactional
    public void bookRooms(RoomBookingRequestDTO roomBookingRequestDTO, int groupId) {
        int tripId = roomBookingRequestDTO.getTripId();

        roomBookingRequestDTO.getRooms().forEach(roomRequest -> {
            String roomType = roomRequest.getRoomType();
            String location = roomRequest.getLocation();
            int requestedQuantity = roomRequest.getQuantity();

            // Step 1: Get all booked room IDs for this trip, type, and location
            List<Integer> bookedRoomIds = roomChargeMapper.getBookedRoomIds(tripId, roomType, location);

            // Step 2: Get all available room IDs for this type and location
            List<Integer> allRoomIds = stateroomMapper.getAvailableRoomIds(roomType, location);

            // Step 3: Get the unbooked room IDs (difference between all and booked)
            Set<Integer> unbookedRoomIds = allRoomIds.stream()
                    .filter(roomId -> !bookedRoomIds.contains(roomId))
                    .collect(Collectors.toSet());

            // Step 4: Ensure there are enough unbooked rooms for the requested quantity
            if (unbookedRoomIds.size() < requestedQuantity) {
                throw new RuntimeException("Not enough rooms available for type: " + roomType + ", location: " + location);
            }

            // Step 5: Create room charge records for the requested quantity
            unbookedRoomIds.stream().limit(requestedQuantity).forEach(roomId -> {
                RoomCharge roomCharge = new RoomCharge();
                roomCharge.setChargeDate(LocalDate.now());
                roomCharge.setRoomId(roomId);
                roomCharge.setTripId(tripId);
                roomCharge.setGroupId(groupId);
                roomChargeMapper.addRoomCharge(roomCharge);
            });

            // Step 6: Reduce the remaining units in room statistics
            roomStatisticsMapper.reduceRoomUnits(tripId, roomType, location, requestedQuantity);
        });
    }
}
