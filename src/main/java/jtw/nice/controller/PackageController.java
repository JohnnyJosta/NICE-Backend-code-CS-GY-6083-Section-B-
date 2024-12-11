package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Package;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PackageController {

    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    /**
     * Get all packages
     * @return
     */
    @Operation(summary = "Get all packages", description = "Retrieve all available packages")
    @GetMapping("/get_packages")
    public ResponseEntity<ApiResponse<List<Package>>> getAllPackages() {
        List<Package> packages = packageService.getAllPackages();
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all packages", packages));
    }

    /**
     * Get package by ID
     * @return
     */
    @Operation(summary = "Get package by ID", description = "Retrieve a package by its ID")
    @GetMapping("/get_package/{id}")
    public ResponseEntity<ApiResponse<Package>> getPackageById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Package pkg = packageService.getPackageById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched package", pkg));
    }

    /**
     * Add a new package
     * @return
     */
    @Operation(summary = "Add a new package", description = "Create a new package record")
    @PostMapping("/add_package")
    public ResponseEntity<ApiResponse<Integer>> addPackage(@RequestBody @Valid Package pkg) {
        int id = packageService.addPackage(pkg);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Package added successfully", id));
    }

    /**
     * Update a package
     * @return
     */
    @Operation(summary = "Update a package", description = "Update an existing package record")
    @PutMapping("/update_package/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePackage(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody @Valid Package pkg) {
        packageService.updatePackage(id, pkg);
        return ResponseEntity.ok(ApiResponse.success("Package updated successfully"));
    }

    /**
     * Delete a package
     * @return
     */
    @Operation(summary = "Delete a package", description = "Remove a package record by ID")
    @DeleteMapping("/delete_package/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePackage(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        packageService.deletePackage(id);
        return ResponseEntity.ok(ApiResponse.success("Package deleted successfully"));
    }
}
