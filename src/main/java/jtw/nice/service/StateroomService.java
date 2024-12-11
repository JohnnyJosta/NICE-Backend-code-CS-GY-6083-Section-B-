package jtw.nice.service;

import jtw.nice.entity.RoomStatistics;
import jtw.nice.entity.Stateroom;
import jtw.nice.entity.dto.RoomBookingRequestDTO;
import jtw.nice.entity.dto.RoomTypeDTO;

import java.util.List;

public interface StateroomService {

    /**
     * Add a new stateroom record.
     *
     * @param room The stateroom entity to add.
     * @return The ID of the newly created stateroom.
     */
    int addStateroom(Stateroom room);

    /**
     * Update an existing stateroom record.
     *
     * @param id The ID of the stateroom to update.
     * @param room The stateroom entity with updated values.
     */
    void updateStateroom(int id, Stateroom room);

    /**
     * Delete a stateroom record by ID.
     *
     * @param id The ID of the stateroom to delete.
     */
    void deleteStateroom(int id);

    /**
     * Get a stateroom record by ID.
     *
     * @param id The ID of the stateroom.
     * @return The stateroom entity.
     */
    Stateroom getStateroomById(int id);

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
     * @return A list of RoomTypeDTO objects containing room statistics for the trip.
     */
    List<RoomTypeDTO> getRoomStatisticsByTripId(int tripId);
}

