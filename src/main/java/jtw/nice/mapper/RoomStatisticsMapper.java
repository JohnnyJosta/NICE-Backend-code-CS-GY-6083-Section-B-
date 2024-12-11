package jtw.nice.mapper;

import jtw.nice.entity.RoomStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomStatisticsMapper {

    int addRoomStatistics(RoomStatistics roomStatistics);

    int updateRoomStatistics(@Param("statId") int statId, @Param("roomStatistics") RoomStatistics roomStatistics);

    int deleteRoomStatistics(int statId);

    RoomStatistics getRoomStatisticsById(int statId);

    List<RoomStatistics> getAllRoomStatistics();

    int reduceRoomUnits(@Param("tripId") int tripId,
                        @Param("roomType") String roomType,
                        @Param("location") String location,
                        @Param("quantity") int quantity);
}
