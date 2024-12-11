package jtw.nice.service;

import jtw.nice.entity.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    /**
     * Add a new Payment.
     *
     * @param payment The Payment entity to add.
     * @return The ID of the newly created Payment.
     */
    int addPayment(Payment payment);

    /**
     * Update an existing Payment.
     *
     * @param paymentId The ID of the Payment to update.
     * @param payment The Payment entity with updated values.
     */
    void updatePayment(int paymentId, Payment payment);

    /**
     * Delete a Payment by ID.
     *
     * @param paymentId The ID of the Payment to delete.
     */
    void deletePayment(int paymentId);

    /**
     * Get a Payment by ID.
     *
     * @param paymentId The ID of the Payment.
     * @return The Payment entity.
     */
    Payment getPaymentById(int paymentId);

    /**
     * Get all Payments.
     *
     * @return A list of all Payment entities.
     */
    List<Payment> getAllPayments();

    /**
     * Process a payment for a specific trip and group.
     *
     * @param tripId The trip ID.
     * @param groupId The group ID.
     * @param paymentAmount The payment amount.
     * @param paymentMethod The payment method.
     * @return Payment entity containing details of the processed payment.
     */
    Payment processPayment(int tripId, int groupId, BigDecimal paymentAmount, String paymentMethod);
}

