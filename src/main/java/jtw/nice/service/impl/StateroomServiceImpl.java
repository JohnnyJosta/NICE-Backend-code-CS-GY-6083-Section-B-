package jtw.nice.service.impl;

import jtw.nice.entity.RoomStatistics;
import jtw.nice.entity.Stateroom;
import jtw.nice.entity.dto.RoomBookingRequestDTO;
import jtw.nice.entity.dto.RoomLocationDTO;
import jtw.nice.entity.dto.RoomTypeDTO;
import jtw.nice.mapper.RoomChargeMapper;
import jtw.nice.mapper.StateroomMapper;
import jtw.nice.service.StateroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateroomServiceImpl implements StateroomService {

    private final StateroomMapper stateroomMapper;
    private final RoomChargeMapper roomChargeMapper;

    @Autowired
    public StateroomServiceImpl(StateroomMapper stateroomMapper, RoomChargeMapper roomChargeMapper) {
        this.stateroomMapper = stateroomMapper;
        this.roomChargeMapper = roomChargeMapper;
    }

    @Override
    public int addStateroom(Stateroom room) {
        stateroomMapper.addStateroom(room);
        return room.getRoomId();
    }

    @Override
    public void updateStateroom(int id, Stateroom room) {
        int rows = stateroomMapper.updateStateroom(id, room);
        if (rows == 0) {
            throw new RuntimeException("Failed to update stateroom with ID: " + id);
        }
    }

    @Override
    public void deleteStateroom(int id) {
        int rows = stateroomMapper.deleteStateroom(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete stateroom with ID: " + id);
        }
    }

    @Override
    public Stateroom getStateroomById(int id) {
        Stateroom room = stateroomMapper.getStateroomById(id);
        if (room == null) {
            throw new RuntimeException("Stateroom not found for ID: " + id);
        }
        return room;
    }

    @Override
    public List<Stateroom> getAllStaterooms() {
        return stateroomMapper.getAllStaterooms();
    }

    @Override
    @Transactional
    public List<RoomTypeDTO> getRoomStatisticsByTripId(int tripId) {
        // 从数据库查询原始数据
        List<Map<String, Object>> roomStats = stateroomMapper.getRoomStatisticsByTripId(tripId);

        // 按 roomType 分组
        Map<String, List<Map<String, Object>>> groupedByRoomType = roomStats.stream()
                .collect(Collectors.groupingBy(map -> (String) map.get("roomType")));

        // 封装为 RoomTypeDTO 列表
        List<RoomTypeDTO> result = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedByRoomType.entrySet()) {
            String roomType = entry.getKey();
            List<Map<String, Object>> locations = entry.getValue();

            // 按价格排序
            List<RoomLocationDTO> locationDTOs = locations.stream()
                    .map(location -> new RoomLocationDTO(
                            (String) location.get("location"),
                            (Integer) location.get("remainingUnits"),
                            (BigDecimal) location.get("price")
                    ))
                    .sorted((a, b) -> a.getPrice().compareTo(b.getPrice())) // 按价格升序
                    .collect(Collectors.toList());

            result.add(new RoomTypeDTO(roomType, locationDTOs));
        }

        return result;
    }
}
