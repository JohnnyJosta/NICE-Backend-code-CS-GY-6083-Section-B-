package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Stopover;
import jtw.nice.entity.Trip;
import jtw.nice.entity.dto.EmployeeStopoverDTO;
import jtw.nice.entity.dto.EmployeeTripDTO;
import jtw.nice.entity.dto.TripDetailsDTO;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * Get all trips
     * @return
     */
    @Operation(summary = "Get all trips", description = "Retrieve all trips")
    @GetMapping("/get_trips")
    public ResponseEntity<ApiResponse<List<Trip>>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all trips", trips));
    }

    /**
     * Get trip by ID
     * @return
     */
    @Operation(summary = "Get trip by ID", description = "Retrieve a trip by its ID")
    @GetMapping("/get_trips/{id}")
    public ResponseEntity<ApiResponse<Trip>> getTripById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched trip", trip));
    }

    /**
     * Add a new trip
     * @return
     */
    @Operation(summary = "Add a new trip", description = "Create a new trip record")
    @PostMapping("/add_employee_trip")
    public ResponseEntity<ApiResponse<Integer>> addTrip(@RequestBody Trip trip) {
        int id = tripService.addTrip(trip);
        return ResponseEntity.ok(ApiResponse.success("Trip added successfully", id));
    }

    /**
     * Update a trip
     * @return
     */
    @Operation(summary = "Update a trip", description = "Update an existing trip record")
    @PutMapping("/update_employee_trip/{id}")
    public ResponseEntity<ApiResponse<Void>> updateTrip(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody Trip trip) {
        tripService.updateTrip(id, trip);
        return ResponseEntity.ok(ApiResponse.success("Trip updated successfully"));
    }

    /**
     * Delete a trip
     * @return
     */
    @Operation(summary = "Delete a trip", description = "Remove a trip record by ID")
    @DeleteMapping("/delete_employee_trip/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrip(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok(ApiResponse.success("Trip deleted successfully"));
    }

    /**
     * Get stopovers by trip ID
     * @return
     */
    @Operation(summary = "Get stopovers by trip ID", description = "Retrieve all stopovers for a specific trip")
    @GetMapping("/get_stopovers")
    public ResponseEntity<ApiResponse<List<EmployeeStopoverDTO>>> getStopoversByTripId(@RequestParam int tripId) {
        List<EmployeeStopoverDTO> stopovers = tripService.getStopoversByTripId(tripId);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched stopovers", stopovers));
    }

    /**
     * Add a stopover to a trip
     * @return
     */
    @Operation(summary = "Add a stopover to a trip", description = "Create a new stopover for a specific trip")
    @PostMapping("/add_stopover")
    public ResponseEntity<ApiResponse<Integer>> addStopover(@RequestBody Stopover stopover) {
        int stopoverId = tripService.addStopover(stopover);
        return ResponseEntity.ok(ApiResponse.success("Stopover added successfully", stopoverId));
    }

    /**
     * Update a stopover
     * @return
     */
    @Operation(summary = "Update a stopover", description = "Update an existing stopover")
    @PutMapping("/update_stopover")
    public ResponseEntity<ApiResponse<Void>> updateStopover(@RequestBody Stopover stopover) {
        tripService.updateStopover(stopover);
        return ResponseEntity.ok(ApiResponse.success("Stopover updated successfully"));
    }

    /**
     * Delete all stopovers for a trip
     * @return
     */
    @Operation(summary = "Delete all stopovers for a trip", description = "Remove all stopovers for a specific trip")
    @DeleteMapping("/delete_stopover/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStopoversByTripId(@PathVariable int id) {
        tripService.deleteStopoversByTripId(id);
        return ResponseEntity.ok(ApiResponse.success("Stopovers deleted successfully"));
    }

    /**
     * Get trip details
     * @return TripDetailsDTO object containing trip details
     */
    @Operation(summary = "Get trip details", description = "Retrieve detailed information for a specific trip")
    @GetMapping("/get_itineraries")
    public ResponseEntity<ApiResponse<List<TripDetailsDTO>>> getTripDetails() {
        System.out.println("Enter controller");
        List<TripDetailsDTO> tripDetails = tripService.getTripDetails();
        System.out.println(tripDetails);
        return ResponseEntity.ok(ApiResponse.success("Trip details fetched successfully", tripDetails));
    }

    /**
     * Get all trips for employees.
     *
     * @return List of trips with port names and details.
     */
    @Operation(summary = "Get employee trips", description = "Fetch all trips for employees with start and end port names.")
    @GetMapping("/get_employee_trips")
    public ResponseEntity<ApiResponse<List<EmployeeTripDTO>>> getEmployeeTrips() {
        List<EmployeeTripDTO> trips = tripService.getEmployeeTrips();
        return ResponseEntity.ok(ApiResponse.success("Employee trips fetched successfully.", trips));
    }
}
