package jtw.nice.service;

import jtw.nice.entity.dto.RoomBookingRequestDTO;

public interface RoomBookingService {

    /**
     * Book rooms for a trip.
     *
     * @param roomBookingRequestDTO The request containing tripId and room booking details.
     * @param groupId The group ID of the booking user.
     */
    void bookRooms(RoomBookingRequestDTO roomBookingRequestDTO, int groupId);
}
