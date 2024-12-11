package jtw.nice.service.impl;

import jtw.nice.entity.RoomCharge;
import jtw.nice.entity.Stateroom;
import jtw.nice.entity.dto.EmployeeRoomDTO;
import jtw.nice.mapper.RoomChargeMapper;
import jtw.nice.mapper.StateroomMapper;
import jtw.nice.service.RoomChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomChargeServiceImpl implements RoomChargeService {

    private final RoomChargeMapper roomChargeMapper;
    private final StateroomMapper stateroomMapper;

    @Autowired
    public RoomChargeServiceImpl(RoomChargeMapper roomChargeMapper, StateroomMapper stateroomMapper) {
        this.roomChargeMapper = roomChargeMapper;
        this.stateroomMapper = stateroomMapper;
    }

    @Override
    public int addRoomCharge(RoomCharge roomCharge) {
        roomChargeMapper.addRoomCharge(roomCharge);
        return roomCharge.getRoomChargeId();
    }

    @Override
    public void updateRoomCharge(int roomChargeId, RoomCharge roomCharge) {
        int rowsAffected = roomChargeMapper.updateRoomCharge(roomChargeId, roomCharge);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update RoomCharge with ID: " + roomChargeId);
        }
    }

    @Override
    public void deleteRoomCharge(int roomChargeId) {
        int rowsAffected = roomChargeMapper.deleteRoomCharge(roomChargeId);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to delete RoomCharge with ID: " + roomChargeId);
        }
    }

    @Override
    public RoomCharge getRoomChargeById(int roomChargeId) {
        RoomCharge roomCharge = roomChargeMapper.getRoomChargeById(roomChargeId);
        if (roomCharge == null) {
            throw new RuntimeException("RoomCharge not found for ID: " + roomChargeId);
        }
        return roomCharge;
    }

    @Override
    public List<RoomCharge> getAllRoomCharges() {
        return roomChargeMapper.getAllRoomCharges();
    }

    @Override
    public List<EmployeeRoomDTO> getEmployeeRoomDTOs(int tripId, int groupId) {
        // Step 1: Fetch room charge data by tripId and groupId
        List<Map<String, Object>> roomCharges = roomChargeMapper.getRoomChargesByTripAndGroup(tripId, groupId);

        // Step 2: Convert room charge data to EmployeeRoomDTO
        return roomCharges.stream().map(charge -> {
            Integer roomChargeId = (Integer) charge.get("roomChargeId");
            Integer roomId = (Integer) charge.get("roomId");
            // Convert java.sql.Date to java.time.LocalDate
            java.sql.Date sqlDate = (java.sql.Date) charge.get("chargeDate");
            LocalDate chargeDate = sqlDate.toLocalDate();

            // Step 3: Fetch room details from Stateroom table
            Stateroom stateroom = stateroomMapper.getStateroomById(roomId);
            String roomName = stateroom.getRoomType();
            BigDecimal price = stateroom.getPrice();

            // Step 4: Create DTO
            EmployeeRoomDTO dto = new EmployeeRoomDTO();
            dto.setRoomChargeId(roomChargeId);
            dto.setRoomName(roomName);
            dto.setChargeDate(chargeDate);
            dto.setPrice(price);

            return dto;
        }).collect(Collectors.toList());
    }
}
