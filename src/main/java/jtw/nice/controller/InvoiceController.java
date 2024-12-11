package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Invoice;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.InvoiceService;
import jtw.nice.service.TripInvoiceService;
import jtw.nice.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invoices")
@Validated
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final TripInvoiceService tripInvoiceService;
    private final UserHolder userHolder;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, TripInvoiceService tripInvoiceService, UserHolder userHolder) {
        this.invoiceService = invoiceService;
        this.tripInvoiceService = tripInvoiceService;
        this.userHolder = userHolder;
    }

    /**
     * Add a new Invoice.
     *
     * @param invoice The Invoice entity to add.
     * @return The ID of the newly created Invoice.
     */
    @Operation(summary = "Add Invoice", description = "Create a new Invoice record.")
    @PostMapping
    public ResponseEntity<ApiResponse<Integer>> addInvoice(@RequestBody @Valid Invoice invoice) {
        int invoiceId = invoiceService.addInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Invoice created successfully", invoiceId));
    }

    /**
     * Update an existing Invoice.
     *
     * @param invoiceId The ID of the Invoice to update.
     * @param invoice The updated Invoice entity.
     * @return A success message.
     */
    @Operation(summary = "Update Invoice", description = "Update an existing Invoice record.")
    @PutMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse<Void>> updateInvoice(
            @PathVariable @Min(value = 1, message = "Invoice ID must be greater than 0") int invoiceId,
            @RequestBody @Valid Invoice invoice) {
        invoiceService.updateInvoice(invoiceId, invoice);
        return ResponseEntity.ok(ApiResponse.success("Invoice updated successfully"));
    }

    /**
     * Delete an Invoice by ID.
     *
     * @param invoiceId The ID of the Invoice to delete.
     * @return A success message.
     */
    @Operation(summary = "Delete Invoice", description = "Delete an Invoice by ID.")
    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(
            @PathVariable @Min(value = 1, message = "Invoice ID must be greater than 0") int invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.ok(ApiResponse.success("Invoice deleted successfully"));
    }

    /**
     * Get an Invoice by ID.
     *
     * @param invoiceId The ID of the Invoice.
     * @return The Invoice entity.
     */
    @Operation(summary = "Get Invoice by ID", description = "Retrieve a specific Invoice record by ID.")
    @GetMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse<Invoice>> getInvoiceById(
            @PathVariable @Min(value = 1, message = "Invoice ID must be greater than 0") int invoiceId) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        return ResponseEntity.ok(ApiResponse.success("Invoice fetched successfully", invoice));
    }

    /**
     * Get all Invoice records.
     *
     * @return A list of all Invoice entities.
     */
    @Operation(summary = "Get all Invoices", description = "Retrieve all Invoice records.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Invoice>>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(ApiResponse.success("All Invoices fetched successfully", invoices));
    }

    /**
     * Get all invoices for the current user's group.
     *
     * @return List of invoices with detailed information.
     */
    @Operation(summary = "Get group invoices", description = "Retrieve all invoices for the current user's group")
    @GetMapping("/group-invoices")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getGroupInvoices() {
        Integer groupId = userHolder.getGroupId();

        if (groupId == null || groupId == 0) {
            return ResponseEntity.status(403)
                    .body(ApiResponse.fail("User does not belong to a valid group."));
        }

        List<Map<String, Object>> invoices = invoiceService.getInvoicesByGroupId(groupId);
        return ResponseEntity.ok(ApiResponse.success("Invoices fetched successfully", invoices));
    }

    /**
     * Generate invoices for all groups in a specific trip.
     *
     * @param tripId The trip ID.
     * @return A success message indicating the invoices were generated.
     */
    @Operation(summary = "Generate invoices for trip", description = "Generate invoices for all groups in a specific trip")
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<String>> generateInvoicesForTrip(@RequestParam int tripId) {
        tripInvoiceService.generateInvoicesForTrip(tripId);
        return ResponseEntity.ok(ApiResponse.success("Invoices generated successfully."));
    }
}

