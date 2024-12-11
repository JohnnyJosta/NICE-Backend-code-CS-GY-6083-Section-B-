package jtw.nice.mapper;

import jtw.nice.entity.RoomStatistics;
import jtw.nice.entity.Stateroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StateroomMapper {
    /**
     * Add a new stateroom record.
     *
     * @param room The stateroom entity to add.
     * @return The number of rows affected.
     */
    int addStateroom(Stateroom room);

    /**
     * Update an existing stateroom record.
     *
     * @param id The ID of the stateroom to update.
     * @param room The stateroom entity with updated values.
     * @return The number of rows affected.
     */
    int updateStateroom(@Param("id") int id, @Param("room") Stateroom room);

    /**
     * Delete a stateroom record by ID.
     *
     * @param roomId The ID of the stateroom to delete.
     * @return The number of rows affected.
     */
    int deleteStateroom(int roomId);

    /**
     * Get a stateroom record by ID.
     *
     * @param roomId The ID of the stateroom.
     * @return The stateroom entity.
     */
    Stateroom getStateroomById(int roomId);

    /**
     * Get all stateroom records.
     *
     * @return A list of all stateroom entities.
     */
    List<Stateroom> getAllStaterooms();

    /**
     * Get room statistical information by trip ID.
     *
     * @param tripId The ID of the trip.
     * @return The remaining number and price of each type of room and location for the trip.
     */
    List<Map<String, Object>> getRoomStatisticsByTripId(@Param("tripId") int tripId);

    /**
     * Get all room IDs for a specific room type and location.
     *
     * @param roomType The type of the room.
     * @param location The location of the room.
     * @return A list of room IDs matching the criteria.
     */
    List<Integer> getAvailableRoomIds(@Param("roomType") String roomType,
                                      @Param("location") String location);
}
