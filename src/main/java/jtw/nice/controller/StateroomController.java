package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.RoomStatistics;
import jtw.nice.entity.Stateroom;
import jtw.nice.entity.dto.RoomTypeDTO;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.StateroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class StateroomController {

    private final StateroomService stateroomService;

    @Autowired
    public StateroomController(StateroomService stateroomService) {
        this.stateroomService = stateroomService;
    }

    /**
     * Get all staterooms
     * @return
     */
    @Operation(summary = "Get all staterooms", description = "Retrieve all staterooms")
    @GetMapping("/get_rooms")
    public ResponseEntity<ApiResponse<List<Stateroom>>> getAllStaterooms() {
        List<Stateroom> staterooms = stateroomService.getAllStaterooms();
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all staterooms", staterooms));
    }

    /**
     * Get stateroom by ID
     * @return
     */
    @Operation(summary = "Get stateroom by ID", description = "Retrieve a stateroom by its ID")
    @GetMapping("/get_room/{id}")
    public ResponseEntity<ApiResponse<Stateroom>> getStateroomById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Stateroom room = stateroomService.getStateroomById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched stateroom", room));
    }

    /**
     * Add a new stateroom
     * @return
     */
    @Operation(summary = "Add a new stateroom", description = "Create a new stateroom record")
    @PostMapping("/add_room")
    public ResponseEntity<ApiResponse<Integer>> addStateroom(@RequestBody Stateroom room) {
        int id = stateroomService.addStateroom(room);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Stateroom added successfully", id));
    }

    /**
     * Update a stateroom
     * @return
     */
    @Operation(summary = "Update a stateroom", description = "Update an existing stateroom record")
    @PutMapping("/update_room/{id}")
    public ResponseEntity<ApiResponse<Void>> updateStateroom(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody @Valid Stateroom room) {
        stateroomService.updateStateroom(id, room);
        return ResponseEntity.ok(ApiResponse.success("Stateroom updated successfully"));
    }

    /**
     * Delete a stateroom
     * @return
     */
    @Operation(summary = "Delete a stateroom", description = "Remove a stateroom record by ID")
    @DeleteMapping("/delete_room/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStateroom(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        stateroomService.deleteStateroom(id);
        return ResponseEntity.ok(ApiResponse.success("Stateroom deleted successfully"));
    }

    /**
     * Count the number of remaining rooms for each type under a specific trip.
     * @param tripId The ID of the trip.
     * @return List of RoomTypeDTO objects containing the room type and the count of remaining rooms.
     */
    @GetMapping("/get_guest_rooms")
    public ResponseEntity<ApiResponse<List<RoomTypeDTO>>> getRoomStatistics(@RequestParam int tripId) {
        List<RoomTypeDTO> roomStatistics = stateroomService.getRoomStatisticsByTripId(tripId);
        return ResponseEntity.ok(ApiResponse.success("Room statistics fetched successfully", roomStatistics));
    }
}
