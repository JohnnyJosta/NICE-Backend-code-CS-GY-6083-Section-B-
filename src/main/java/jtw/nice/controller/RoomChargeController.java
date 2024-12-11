package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.RoomCharge;
import jtw.nice.entity.dto.EmployeeRoomDTO;
import jtw.nice.entity.dto.TransactionDTO;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.RoomChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class RoomChargeController {

    private final RoomChargeService roomChargeService;

    @Autowired
    public RoomChargeController(RoomChargeService roomChargeService) {
        this.roomChargeService = roomChargeService;
    }

    /**
     * Add a new RoomCharge.
     *
     * @param transactionDTO The RoomCharge entity to add.
     * @return The ID of the newly created RoomCharge.
     */
    @Operation(summary = "Add RoomCharge", description = "Create a new RoomCharge record.")
    @PostMapping("/add_room_charge")
    public ResponseEntity<ApiResponse<Integer>> addRoomCharge(@RequestBody TransactionDTO transactionDTO) {
        System.out.println(transactionDTO.getItemId());
        RoomCharge roomCharge = new RoomCharge();
        roomCharge.setChargeDate(transactionDTO.getDate());
        roomCharge.setRoomId(transactionDTO.getItemId());
        roomCharge.setTripId(transactionDTO.getTripId());
        roomCharge.setGroupId(transactionDTO.getGroupId());
        int roomChargeId = roomChargeService.addRoomCharge(roomCharge);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("RoomCharge created successfully", roomChargeId));
    }

    /**
     * Update an existing RoomCharge.
     *
     * @param roomChargeId The ID of the RoomCharge to update.
     * @param roomCharge The updated RoomCharge entity.
     * @return A success message.
     */
    @Operation(summary = "Update RoomCharge", description = "Update an existing RoomCharge record.")
    @PutMapping("/update_room_charge/{roomChargeId}")
    public ResponseEntity<ApiResponse<Void>> updateRoomCharge(
            @PathVariable @Min(value = 1, message = "RoomCharge ID must be greater than 0") int roomChargeId,
            @RequestBody RoomCharge roomCharge) {
        roomChargeService.updateRoomCharge(roomChargeId, roomCharge);
        return ResponseEntity.ok(ApiResponse.success("RoomCharge updated successfully"));
    }

    /**
     * Delete a RoomCharge by ID.
     *
     * @param roomChargeId The ID of the RoomCharge to delete.
     * @return A success message.
     */
    @Operation(summary = "Delete RoomCharge", description = "Delete a RoomCharge by ID.")
    @DeleteMapping("/delete_room_charge/{roomChargeId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoomCharge(
            @PathVariable @Min(value = 1, message = "RoomCharge ID must be greater than 0") int roomChargeId) {
        roomChargeService.deleteRoomCharge(roomChargeId);
        return ResponseEntity.ok(ApiResponse.success("RoomCharge deleted successfully"));
    }

    /**
     * Get a RoomCharge by ID.
     *
     * @param roomChargeId The ID of the RoomCharge.
     * @return The RoomCharge entity.
     */
    @Operation(summary = "Get RoomCharge by ID", description = "Retrieve a specific RoomCharge record by ID.")
    @GetMapping("/get_a_room_charge/{roomChargeId}")
    public ResponseEntity<ApiResponse<RoomCharge>> getRoomChargeById(
            @PathVariable @Min(value = 1, message = "RoomCharge ID must be greater than 0") int roomChargeId) {
        RoomCharge roomCharge = roomChargeService.getRoomChargeById(roomChargeId);
        return ResponseEntity.ok(ApiResponse.success("RoomCharge fetched successfully", roomCharge));
    }

    /**
     * Get all RoomCharge records.
     *
     * @return A list of all RoomCharge entities.
     */
    @Operation(summary = "Get all RoomCharges", description = "Retrieve all RoomCharge records.")
    @GetMapping("/room_charges/all")
    public ResponseEntity<ApiResponse<List<RoomCharge>>> getAllRoomCharges() {
        List<RoomCharge> roomCharges = roomChargeService.getAllRoomCharges();
        return ResponseEntity.ok(ApiResponse.success("All RoomCharges fetched successfully", roomCharges));
    }

    /**
     * Get all RoomCharge records.
     *
     * @return A list of all RoomCharge entities.
     */
    @Operation(summary = "Get all RoomCharges", description = "Retrieve all RoomCharge records.")
    @GetMapping("/get_room_charges")
    public ResponseEntity<ApiResponse<List<EmployeeRoomDTO>>> getRoomChargesByTripIdAndGroupId(@RequestParam int tripId, @RequestParam int groupId) {
        List<EmployeeRoomDTO> roomCharges = roomChargeService.getEmployeeRoomDTOs(tripId, groupId);
        return ResponseEntity.ok(ApiResponse.success("All RoomCharges fetched successfully", roomCharges));
    }
}
