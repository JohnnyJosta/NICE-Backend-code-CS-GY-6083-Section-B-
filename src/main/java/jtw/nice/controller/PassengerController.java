package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Passenger;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * Get all passengers
     * @return
     */
    @Operation(summary = "Get all passengers", description = "Retrieve all passengers")
    @GetMapping("/get_passengers")
    public ResponseEntity<ApiResponse<List<Passenger>>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all passengers", passengers));
    }

    /**
     * Get passenger by ID
     * @return
     */
    @Operation(summary = "Get passenger by ID", description = "Retrieve a passenger by its ID")
    @GetMapping("/get_passenger/{id}")
    public ResponseEntity<ApiResponse<Passenger>> getPassengerById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Passenger passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched passenger", passenger));
    }

    /**
     * Add a new passenger
     * @return
     */
    @Operation(summary = "Add a new passenger", description = "Create a new passenger record")
    @PostMapping("/add_passenger")
    public ResponseEntity<ApiResponse<Integer>> addPassenger(@RequestBody @Valid Passenger passenger) {
        int id = passengerService.addPassenger(passenger);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Passenger added successfully", id));
    }

    /**
     * Update a passenger
     * @return
     */
    @Operation(summary = "Update a passenger", description = "Update an existing passenger record")
    @PutMapping("/update_passenger/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePassenger(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody @Valid Passenger passenger) {
        passengerService.updatePassenger(id, passenger);
        return ResponseEntity.ok(ApiResponse.success("Passenger updated successfully"));
    }

    /**
     * Delete a passenger
     * @return
     */
    @Operation(summary = "Delete a passenger", description = "Remove a passenger record by ID")
    @DeleteMapping("/delete_passenger/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePassenger(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.ok(ApiResponse.success("Passenger deleted successfully"));
    }
}
