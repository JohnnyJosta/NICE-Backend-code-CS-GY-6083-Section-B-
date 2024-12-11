package jtw.nice.mapper;

import jtw.nice.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {

    /**
     * Add a new Payment.
     *
     * @param payment The Payment entity to add.
     * @return The number of rows affected.
     */
    int addPayment(Payment payment);

    /**
     * Update an existing Payment.
     *
     * @param paymentId The ID of the Payment to update.
     * @param payment The Payment entity with updated values.
     * @return The number of rows affected.
     */
    int updatePayment(@Param("paymentId") int paymentId, @Param("payment") Payment payment);

    /**
     * Delete a Payment by ID.
     *
     * @param paymentId The ID of the Payment to delete.
     * @return The number of rows affected.
     */
    int deletePayment(int paymentId);

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
}

