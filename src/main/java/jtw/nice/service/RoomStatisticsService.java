package jtw.nice.service;

import jtw.nice.entity.RoomStatistics;

import java.util.List;

public interface RoomStatisticsService {

    int addRoomStatistics(RoomStatistics roomStatistics);

    void updateRoomStatistics(int statId, RoomStatistics roomStatistics);

    void deleteRoomStatistics(int statId);

    RoomStatistics getRoomStatisticsById(int statId);

    List<RoomStatistics> getAllRoomStatistics();

    void reduceRoomUnits(int tripId, String roomType, String location, int quantity);
}
