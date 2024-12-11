package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jtw.nice.entity.TripEntertainment;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.TripEntertainmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripEntertainmentController {

    private final TripEntertainmentService tripEntertainmentService;

    @Autowired
    public TripEntertainmentController(TripEntertainmentService tripEntertainmentService) {
        this.tripEntertainmentService = tripEntertainmentService;
    }

    @Operation(summary = "Add Trip Entertainment", description = "Add an entertainment to a trip.")
    @PostMapping("/add_trip_entertainment")
    public ResponseEntity<ApiResponse<Void>> addTripEntertainment(@RequestBody TripEntertainment tripEntertainment) {
        tripEntertainmentService.addTripEntertainment(tripEntertainment);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Trip entertainment added successfully"));
    }

    @Operation(summary = "Update Trip Entertainment", description = "Update the quantity of an entertainment for a trip.")
    @PutMapping("/update_trip_entertainment")
    public ResponseEntity<ApiResponse<Void>> updateTripEntertainment(@RequestBody TripEntertainment tripEntertainment) {
        tripEntertainmentService.updateTripEntertainment(tripEntertainment.getTripId(), tripEntertainment.getEntertainmentId(), tripEntertainment.getQuantity());
        return ResponseEntity.ok(ApiResponse.success("Trip entertainment updated successfully"));
    }

    @Operation(summary = "Delete Trip Entertainment", description = "Delete an entertainment from a trip.")
    @DeleteMapping("/delete_trip_entertainment")
    public ResponseEntity<ApiResponse<Void>> deleteTripEntertainment(@RequestParam int tripId,
                                                                     @RequestParam int entertainmentId) {
        tripEntertainmentService.deleteTripEntertainment(tripId, entertainmentId);
        return ResponseEntity.ok(ApiResponse.success("Trip entertainment deleted successfully"));
    }

    @Operation(summary = "Get Trip Entertainments", description = "Get all entertainments for a trip.")
    @GetMapping("/get_trip_entertainments")
    public ResponseEntity<ApiResponse<List<TripEntertainment>>> getTripEntertainmentsByTripId(@RequestParam int tripId) {
        List<TripEntertainment> entertainments = tripEntertainmentService.getTripEntertainmentsByTripId(tripId);
        return ResponseEntity.ok(ApiResponse.success("Trip entertainments fetched successfully", entertainments));
    }
}
