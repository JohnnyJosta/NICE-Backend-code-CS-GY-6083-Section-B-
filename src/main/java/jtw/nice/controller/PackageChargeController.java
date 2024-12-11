package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.PackageCharge;
import jtw.nice.entity.dto.EmployeePackageDTO;
import jtw.nice.entity.dto.EmployeeRoomDTO;
import jtw.nice.entity.dto.TransactionDTO;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.PackageChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PackageChargeController {

    private final PackageChargeService packageChargeService;

    @Autowired
    public PackageChargeController(PackageChargeService packageChargeService) {
        this.packageChargeService = packageChargeService;
    }

    /**
     * Add a new PackageCharge.
     *
     * @param transactionDTO The PackageCharge entity to add.
     * @return The ID of the newly created PackageCharge.
     */
    @Operation(summary = "Add PackageCharge", description = "Create a new PackageCharge record.")
    @PostMapping("/add_package_charge")
    public ResponseEntity<ApiResponse<Integer>> addPackageCharge(@RequestBody TransactionDTO transactionDTO) {
        PackageCharge packageCharge = new PackageCharge();
        packageCharge.setChargeDate(transactionDTO.getDate());
        packageCharge.setPackageId(transactionDTO.getItemId());
        packageCharge.setTripId(transactionDTO.getTripId());
        packageCharge.setGroupId(transactionDTO.getGroupId());
        int packageChargeId = packageChargeService.addPackageCharge(packageCharge);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("PackageCharge created successfully", packageChargeId));
    }

    /**
     * Update an existing PackageCharge.
     *
     * @param packageChargeId The ID of the PackageCharge to update.
     * @param packageCharge The updated PackageCharge entity.
     * @return A success message.
     */
    @Operation(summary = "Update PackageCharge", description = "Update an existing PackageCharge record.")
    @PutMapping("/update_package_charge/{packageChargeId}")
    public ResponseEntity<ApiResponse<Void>> updatePackageCharge(
            @PathVariable @Min(value = 1, message = "PackageCharge ID must be greater than 0") int packageChargeId,
            @RequestBody PackageCharge packageCharge) {
        packageChargeService.updatePackageCharge(packageChargeId, packageCharge);
        return ResponseEntity.ok(ApiResponse.success("PackageCharge updated successfully"));
    }

    /**
     * Delete a PackageCharge by ID.
     *
     * @param packageChargeId The ID of the PackageCharge to delete.
     * @return A success message.
     */
    @Operation(summary = "Delete PackageCharge", description = "Delete a PackageCharge by ID.")
    @DeleteMapping("/delete_package_charge/{packageChargeId}")
    public ResponseEntity<ApiResponse<Void>> deletePackageCharge(
            @PathVariable @Min(value = 1, message = "PackageCharge ID must be greater than 0") int packageChargeId) {
        packageChargeService.deletePackageCharge(packageChargeId);
        return ResponseEntity.ok(ApiResponse.success("PackageCharge deleted successfully"));
    }

    /**
     * Get a PackageCharge by ID.
     *
     * @param packageChargeId The ID of the PackageCharge.
     * @return The PackageCharge entity.
     */
    @Operation(summary = "Get PackageCharge by ID", description = "Retrieve a specific PackageCharge record by ID.")
    @GetMapping("get_a_room_charge/{packageChargeId}")
    public ResponseEntity<ApiResponse<PackageCharge>> getPackageChargeById(
            @PathVariable @Min(value = 1, message = "PackageCharge ID must be greater than 0") int packageChargeId) {
        PackageCharge packageCharge = packageChargeService.getPackageChargeById(packageChargeId);
        return ResponseEntity.ok(ApiResponse.success("PackageCharge fetched successfully", packageCharge));
    }

    /**
     * Get all PackageCharge records.
     *
     * @return A list of all PackageCharge entities.
     */
    @Operation(summary = "Get all PackageCharges", description = "Retrieve all PackageCharge records.")
    @GetMapping("/get_all_package_charges")
    public ResponseEntity<ApiResponse<List<PackageCharge>>> getAllPackageCharges() {
        List<PackageCharge> packageCharges = packageChargeService.getAllPackageCharges();
        return ResponseEntity.ok(ApiResponse.success("All PackageCharges fetched successfully", packageCharges));
    }

    /**
     * Get all RoomCharge records.
     *
     * @return A list of all RoomCharge entities.
     */
    @Operation(summary = "Get all RoomCharges", description = "Retrieve all RoomCharge records.")
    @GetMapping("/get_package_charges")
    public ResponseEntity<ApiResponse<List<EmployeePackageDTO>>> getPackagesByTripIdAndGroupId(@RequestParam int tripId, @RequestParam int groupId) {
        List<EmployeePackageDTO> roomCharges = packageChargeService.getEmployeePackageDTOs(tripId, groupId);
        return ResponseEntity.ok(ApiResponse.success("All RoomCharges fetched successfully", roomCharges));
    }
}
