package jtw.nice.mapper;

import jtw.nice.entity.RoomCharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface RoomChargeMapper {

    /**
     * Add a new RoomCharge.
     *
     * @param roomCharge The RoomCharge entity to add.
     * @return The number of rows affected.
     */
    int addRoomCharge(RoomCharge roomCharge);

    /**
     * Update an existing RoomCharge.
     *
     * @param roomChargeId The ID of the RoomCharge to update.
     * @param roomCharge The RoomCharge entity with updated values.
     * @return The number of rows affected.
     */
    int updateRoomCharge(@Param("roomChargeId") int roomChargeId, @Param("roomCharge") RoomCharge roomCharge);

    /**
     * Delete a RoomCharge by ID.
     *
     * @param roomChargeId The ID of the RoomCharge to delete.
     * @return The number of rows affected.
     */
    int deleteRoomCharge(int roomChargeId);

    /**
     * Get a RoomCharge by ID.
     *
     * @param roomChargeId The ID of the RoomCharge.
     * @return The RoomCharge entity.
     */
    RoomCharge getRoomChargeById(int roomChargeId);

    /**
     * Get all RoomCharges.
     *
     * @return A list of all RoomCharge entities.
     */
    List<RoomCharge> getAllRoomCharges();

    /**
     * Get all booked room IDs for a specific trip, room type, and location.
     *
     * @param tripId The trip ID.
     * @param roomType The type of the room.
     * @param location The location of the room.
     * @return A list of room IDs already booked for the trip.
     */
    List<Integer> getBookedRoomIds(@Param("tripId") int tripId,
                                   @Param("roomType") String roomType,
                                   @Param("location") String location);

    /**
     * Get all room charges for a specific trip and group.
     *
     * @param tripId The trip ID.
     * @param groupId The group ID.
     * @return A list of room charges.
     */
    List<Map<String, Object>> getRoomChargesByTripAndGroupId(@Param("tripId") int tripId, @Param("groupId") int groupId);

    List<Map<String, Object>> getRoomChargesByTripAndGroup(@Param("tripId") int tripId, @Param("groupId") int groupId);
}
