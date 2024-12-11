package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jtw.nice.entity.RoomStatistics;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.RoomStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-statistics")
@Validated
public class RoomStatisticsController {

    private final RoomStatisticsService roomStatisticsService;

    @Autowired
    public RoomStatisticsController(RoomStatisticsService roomStatisticsService) {
        this.roomStatisticsService = roomStatisticsService;
    }

    @Operation(summary = "Add Room Statistics", description = "Create a new room statistics record.")
    @PostMapping
    public ResponseEntity<ApiResponse<Integer>> addRoomStatistics(@RequestBody @Valid RoomStatistics roomStatistics) {
        int statId = roomStatisticsService.addRoomStatistics(roomStatistics);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("RoomStatistics created successfully", statId));
    }

    @Operation(summary = "Update Room Statistics", description = "Update an existing room statistics record.")
    @PutMapping("/{statId}")
    public ResponseEntity<ApiResponse<Void>> updateRoomStatistics(
            @PathVariable int statId, @RequestBody @Valid RoomStatistics roomStatistics) {
        roomStatisticsService.updateRoomStatistics(statId, roomStatistics);
        return ResponseEntity.ok(ApiResponse.success("RoomStatistics updated successfully"));
    }

    @Operation(summary = "Delete Room Statistics", description = "Delete a room statistics record by ID.")
    @DeleteMapping("/{statId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoomStatistics(@PathVariable int statId) {
        roomStatisticsService.deleteRoomStatistics(statId);
        return ResponseEntity.ok(ApiResponse.success("RoomStatistics deleted successfully"));
    }

    @Operation(summary = "Get Room Statistics by ID", description = "Retrieve a specific room statistics record by ID.")
    @GetMapping("/{statId}")
    public ResponseEntity<ApiResponse<RoomStatistics>> getRoomStatisticsById(@PathVariable int statId) {
        RoomStatistics roomStatistics = roomStatisticsService.getRoomStatisticsById(statId);
        return ResponseEntity.ok(ApiResponse.success("RoomStatistics fetched successfully", roomStatistics));
    }

    @Operation(summary = "Get All Room Statistics", description = "Retrieve all room statistics records.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomStatistics>>> getAllRoomStatistics() {
        List<RoomStatistics> roomStatisticsList = roomStatisticsService.getAllRoomStatistics();
        return ResponseEntity.ok(ApiResponse.success("All RoomStatistics fetched successfully", roomStatisticsList));
    }
}
