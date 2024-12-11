package jtw.nice.service.impl;

import jtw.nice.entity.RoomStatistics;
import jtw.nice.mapper.RoomStatisticsMapper;
import jtw.nice.service.RoomStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomStatisticsServiceImpl implements RoomStatisticsService {

    private final RoomStatisticsMapper roomStatisticsMapper;

    @Autowired
    public RoomStatisticsServiceImpl(RoomStatisticsMapper roomStatisticsMapper) {
        this.roomStatisticsMapper = roomStatisticsMapper;
    }

    @Override
    public int addRoomStatistics(RoomStatistics roomStatistics) {
        roomStatisticsMapper.addRoomStatistics(roomStatistics);
        return roomStatistics.getStatId();
    }

    @Override
    public void updateRoomStatistics(int statId, RoomStatistics roomStatistics) {
        int rows = roomStatisticsMapper.updateRoomStatistics(statId, roomStatistics);
        if (rows == 0) {
            throw new RuntimeException("Failed to update RoomStatistics with ID: " + statId);
        }
    }

    @Override
    public void deleteRoomStatistics(int statId) {
        int rows = roomStatisticsMapper.deleteRoomStatistics(statId);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete RoomStatistics with ID: " + statId);
        }
    }

    @Override
    public RoomStatistics getRoomStatisticsById(int statId) {
        RoomStatistics roomStatistics = roomStatisticsMapper.getRoomStatisticsById(statId);
        if (roomStatistics == null) {
            throw new RuntimeException("RoomStatistics not found for ID: " + statId);
        }
        return roomStatistics;
    }

    @Override
    public List<RoomStatistics> getAllRoomStatistics() {
        return roomStatisticsMapper.getAllRoomStatistics();
    }

    @Override
    @Transactional
    public void reduceRoomUnits(int tripId, String roomType, String location, int quantity) {
        int rows = roomStatisticsMapper.reduceRoomUnits(tripId, roomType, location, quantity);
        if (rows == 0) {
            throw new RuntimeException("Not enough remaining units to reduce for type: " + roomType + ", location: " + location);
        }
    }
}
