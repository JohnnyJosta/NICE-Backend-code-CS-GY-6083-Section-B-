package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Payment;
import jtw.nice.entity.dto.PaymentRequest;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.PaymentService;
import jtw.nice.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Validated
public class PaymentController {

    private final PaymentService paymentService;
    private final UserHolder userHolder;

    @Autowired
    public PaymentController(PaymentService paymentService, UserHolder userHolder) {
        this.paymentService = paymentService;
        this.userHolder = userHolder;
    }

    /**
     * Add a new Payment.
     *
     * @param payment The Payment entity to add.
     * @return The ID of the newly created Payment.
     */
    @Operation(summary = "Add Payment", description = "Create a new Payment record.")
    @PostMapping("/payments")
    public ResponseEntity<ApiResponse<Integer>> addPayment(@RequestBody @Valid Payment payment) {
        int paymentId = paymentService.addPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payment created successfully", paymentId));
    }

    /**
     * Update an existing Payment.
     *
     * @param paymentId The ID of the Payment to update.
     * @param payment The updated Payment entity.
     * @return A success message.
     */
    @Operation(summary = "Update Payment", description = "Update an existing Payment record.")
    @PutMapping("/payments/{paymentId}")
    public ResponseEntity<ApiResponse<Void>> updatePayment(
            @PathVariable @Min(value = 1, message = "Payment ID must be greater than 0") int paymentId,
            @RequestBody @Valid Payment payment) {
        paymentService.updatePayment(paymentId, payment);
        return ResponseEntity.ok(ApiResponse.success("Payment updated successfully"));
    }

    /**
     * Delete a Payment by ID.
     *
     * @param paymentId The ID of the Payment to delete.
     * @return A success message.
     */
    @Operation(summary = "Delete Payment", description = "Delete a Payment by ID.")
    @DeleteMapping("/payments/{paymentId}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(
            @PathVariable @Min(value = 1, message = "Payment ID must be greater than 0") int paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.ok(ApiResponse.success("Payment deleted successfully"));
    }

    /**
     * Get a Payment by ID.
     *
     * @param paymentId The ID of the Payment.
     * @return The Payment entity.
     */
    @Operation(summary = "Get Payment by ID", description = "Retrieve a specific Payment record by ID.")
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentById(
            @PathVariable @Min(value = 1, message = "Payment ID must be greater than 0") int paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(ApiResponse.success("Payment fetched successfully", payment));
    }

    /**
     * Get all Payment records.
     *
     * @return A list of all Payment entities.
     */
    @Operation(summary = "Get all Payments", description = "Retrieve all Payment records.")
    @GetMapping("get_all_payments")
    public ResponseEntity<ApiResponse<List<Payment>>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.success("All Payments fetched successfully", payments));
    }

    /**
     * Process a payment for a specific trip.
     *
     * @param paymentRequest The payment details from the request body.
     * @return Response indicating success or failure.
     */
    @Operation(summary = "Pay bill", description = "Process a payment for a specific trip.")
    @PostMapping("/pay_bill")
    public ResponseEntity<ApiResponse<String>> payBill(@RequestBody PaymentRequest paymentRequest) {
        int tripId = paymentRequest.getTripId();
        Integer groupId = userHolder.getGroupId();
        if (groupId == null || groupId == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.fail("User does not belong to a valid group."));
        }

        // Extract payment details
        BigDecimal paymentAmount = paymentRequest.getPaymentAmount();
        String paymentMethod = paymentRequest.getPaymentMethod();

        // Process payment
        paymentService.processPayment(tripId, groupId, paymentAmount, paymentMethod);

        return ResponseEntity.ok(ApiResponse.success("Payment successful."));
    }
}
