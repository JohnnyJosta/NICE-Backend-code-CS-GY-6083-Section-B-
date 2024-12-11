package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jtw.nice.entity.dto.RoomBookingRequestDTO;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.RoomBookingService;
import jtw.nice.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomBookingController {

    private final RoomBookingService roomBookingService;
    private final UserHolder userHolder;

    @Autowired
    public RoomBookingController(RoomBookingService roomBookingService, UserHolder userHolder) {
        this.roomBookingService = roomBookingService;
        this.userHolder = userHolder;
    }

    /**
     * Book rooms for a trip.
     *
     * @param roomBookingRequestDTO The room booking request payload.
     * @return Success or error message.
     */
    @Operation(summary = "Book rooms", description = "Allows users to book rooms for a specific trip.")
    @PostMapping("/order")
    public ResponseEntity<ApiResponse<Void>> bookRooms(
            @RequestBody RoomBookingRequestDTO roomBookingRequestDTO) {
        int groupId = userHolder.getGroupId();
        if (groupId == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.fail("User group information not found. Unable to book rooms."));
        }

        try {
            roomBookingService.bookRooms(roomBookingRequestDTO, groupId);
            return ResponseEntity.ok(ApiResponse.success("Booking successfully submitted"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail("Room booking failed: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("An unexpected error occurred: " + e.getMessage()));
        }
    }
}
