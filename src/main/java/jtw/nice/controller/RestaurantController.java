package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Restaurant;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.RestaurantService;
import jtw.nice.service.impl.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Get all restaurants
     * @return
     */
    @Operation(summary = "Get all restaurants", description = "Retrieve all restaurants")
    @GetMapping("/get_restaurants")
    public ResponseEntity<ApiResponse<List<Restaurant>>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all restaurants", restaurants));
    }

    /**
     * Get restaurant by ID
     * @return
     */
    @Operation(summary = "Get restaurant by ID", description = "Retrieve a restaurant by its ID")
    @GetMapping("/get_restaurant/{id}")
    public ResponseEntity<ApiResponse<Restaurant>> getRestaurantById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched restaurant", restaurant));
    }

    /**
     * Add a new restaurant
     * @return
     */
    @Operation(summary = "Add a new restaurant", description = "Create a new restaurant record")
    @PostMapping("/add_restaurant")
    public ResponseEntity<ApiResponse<Integer>> addRestaurant(@RequestBody Restaurant restaurant) {
        int id = restaurantService.addRestaurant(restaurant);
        return ResponseEntity.ok(ApiResponse.success("Restaurant added successfully", id));
    }

    /**
     * Update a restaurant
     * @return
     */
    @Operation(summary = "Update a restaurant", description = "Update an existing restaurant record")
    @PutMapping("/update_restaurant/{id}")
    public ResponseEntity<ApiResponse<Void>> updateRestaurant(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody @Valid Restaurant restaurant) {
        restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok(ApiResponse.success("Restaurant updated successfully"));
    }

    /**
     * Delete a restaurant
     * @return
     */
    @Operation(summary = "Delete a restaurant", description = "Remove a restaurant record by ID")
    @DeleteMapping("/delete_restaurant/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRestaurant(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok(ApiResponse.success("Restaurant deleted successfully"));
    }
}
