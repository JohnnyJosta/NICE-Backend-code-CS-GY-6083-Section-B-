package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jtw.nice.entity.TripRestaurant;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.TripRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripRestaurantController {

    private final TripRestaurantService tripRestaurantService;

    @Autowired
    public TripRestaurantController(TripRestaurantService tripRestaurantService) {
        this.tripRestaurantService = tripRestaurantService;
    }

    @Operation(summary = "Add Trip Restaurant", description = "Add a restaurant to a trip.")
    @PostMapping("/add_trip_restaurant")
    public ResponseEntity<ApiResponse<Void>> addTripRestaurant(@RequestBody TripRestaurant tripRestaurant) {
        tripRestaurantService.addTripRestaurant(tripRestaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Trip restaurant added successfully"));
    }

    @Operation(summary = "Update Trip Restaurant", description = "Update the quantity of a restaurant for a trip.")
    @PutMapping("/update_trip_restaurant")
    public ResponseEntity<ApiResponse<Void>> updateTripRestaurant(@RequestBody TripRestaurant tripRestaurant) {
        tripRestaurantService.updateTripRestaurant(tripRestaurant.getTripId(), tripRestaurant.getRestaurantId(), tripRestaurant.getQuantity());
        return ResponseEntity.ok(ApiResponse.success("Trip restaurant updated successfully"));
    }

    @Operation(summary = "Delete Trip Restaurant", description = "Delete a restaurant from a trip.")
    @DeleteMapping("/delete_trip_restaurant")
    public ResponseEntity<ApiResponse<Void>> deleteTripRestaurant(@RequestParam int tripId,
                                                                  @RequestParam int restaurantId) {
        tripRestaurantService.deleteTripRestaurant(tripId, restaurantId);
        return ResponseEntity.ok(ApiResponse.success("Trip restaurant deleted successfully"));
    }

    @Operation(summary = "Get Trip Restaurants", description = "Get all restaurants for a trip.")
    @GetMapping("/get_trip_restaurants")
    public ResponseEntity<ApiResponse<List<TripRestaurant>>> getTripRestaurantsByTripId(@RequestParam int tripId) {
        List<TripRestaurant> restaurants = tripRestaurantService.getTripRestaurantsByTripId(tripId);
        return ResponseEntity.ok(ApiResponse.success("Trip restaurants fetched successfully", restaurants));
    }
}

