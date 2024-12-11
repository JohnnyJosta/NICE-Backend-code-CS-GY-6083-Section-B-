package jtw.nice.service;

import jtw.nice.entity.RoomCharge;
import jtw.nice.entity.dto.EmployeeRoomDTO;

import java.util.List;

public interface RoomChargeService {

    /**
     * Add a new RoomCharge.
     *
     * @param roomCharge The RoomCharge entity to add.
     * @return The ID of the newly created RoomCharge.
     */
    int addRoomCharge(RoomCharge roomCharge);

    /**
     * Update an existing RoomCharge.
     *
     * @param roomChargeId The ID of the RoomCharge to update.
     * @param roomCharge The RoomCharge entity with updated values.
     */
    void updateRoomCharge(int roomChargeId, RoomCharge roomCharge);

    /**
     * Delete a RoomCharge by ID.
     *
     * @param roomChargeId The ID of the RoomCharge to delete.
     */
    void deleteRoomCharge(int roomChargeId);

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

    List<EmployeeRoomDTO> getEmployeeRoomDTOs(int tripId, int groupId);
}
