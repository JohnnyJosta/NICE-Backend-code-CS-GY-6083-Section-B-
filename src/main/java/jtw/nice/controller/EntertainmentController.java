package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Entertainment;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.EntertainmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class EntertainmentController {

    private final EntertainmentService entertainmentService;

    @Autowired
    public EntertainmentController(EntertainmentService entertainmentService) {
        this.entertainmentService = entertainmentService;
    }

    /**
     * Get all entertainments
     * @return
     */
    @Operation(summary = "Get all entertainments", description = "Retrieve all entertainments")
    @GetMapping("/get_entertainments")
    public ResponseEntity<ApiResponse<List<Entertainment>>> getAllEntertainments() {
        List<Entertainment> entertainments = entertainmentService.getAllEntertainments();
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all entertainments", entertainments));
    }

    /**
     * Get entertainment by ID
     * @return
     */
    @Operation(summary = "Get entertainment by ID", description = "Retrieve an entertainment by its ID")
    @GetMapping("/get_entertainment/{id}")
    public ResponseEntity<ApiResponse<Entertainment>> getEntertainmentById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Entertainment entertainment = entertainmentService.getEntertainmentById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched entertainment", entertainment));
    }

    /**
     * Add a new entertainment
     * @return
     */
    @Operation(summary = "Add a new entertainment", description = "Create a new entertainment record")
    @PostMapping("add_entertainment")
    public ResponseEntity<ApiResponse<Integer>> addEntertainment(@RequestBody @Valid Entertainment entertainment) {
        int id = entertainmentService.addEntertainment(entertainment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Entertainment added successfully", id));
    }

    /**
     * Update an entertainment
     * @return
     */
    @Operation(summary = "Update an entertainment", description = "Update an existing entertainment record")
    @PutMapping("update_entertainment/{id}")
    public ResponseEntity<ApiResponse<Void>> updateEntertainment(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody @Valid Entertainment entertainment) {
        entertainmentService.updateEntertainment(id, entertainment);
        return ResponseEntity.ok(ApiResponse.success("Entertainment updated successfully"));
    }

    /**
     * Delete an entertainment
     * @return
     */
    @Operation(summary = "Delete an entertainment", description = "Remove an entertainment record by ID")
    @DeleteMapping("delete_entertainment/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEntertainment(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        entertainmentService.deleteEntertainment(id);
        return ResponseEntity.ok(ApiResponse.success("Entertainment deleted successfully"));
    }
}
