package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Port;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Validated
public class PortController {

    private final PortService portService;

    @Autowired
    public PortController(PortService portService) {
        this.portService = portService;
    }

    /**
     * Get all ports
     * @return
     */
    @Operation(summary = "Get all ports", description = "Retrieve all ports")
    @GetMapping("/get_ports")
    public ResponseEntity<ApiResponse<List<Port>>> getAllPorts() {
        List<Port> ports = portService.getAllPorts();
        System.out.println(ports.size());
        ports.forEach(port -> System.out.println(port));
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all ports", ports));
    }

    /**
     * Get port by ID
     * @return
     */
    @Operation(summary = "Get port by ID", description = "Retrieve a port by its ID")
    @GetMapping("/get_port/{id}")
    public ResponseEntity<ApiResponse<Port>> getPortById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Port port = portService.getPortById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched port", port));
    }

    /**
     * Add a new port
     * @return
     */
    @Operation(summary = "Add a new port", description = "Create a new port record")
    @PostMapping("/add_port")
    public ResponseEntity<ApiResponse<Integer>> addPort(@RequestBody Port port) {
        int id = portService.addPort(port);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Port added successfully", id));
    }

    /**
     * Update a port
     * @return
     */
    @Operation(summary = "Update a port", description = "Update an existing port record")
    @PutMapping("/update_port/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePort(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody @Valid Port port) {
        portService.updatePort(id, port);
        return ResponseEntity.ok(ApiResponse.success("Port updated successfully"));
    }

    /**
     * Delete a port
     * @return
     */
    @Operation(summary = "Delete a port", description = "Remove a port record by ID")
    @DeleteMapping("delete_port/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePort(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        portService.deletePort(id);
        return ResponseEntity.ok(ApiResponse.success("Port deleted successfully"));
    }
}
