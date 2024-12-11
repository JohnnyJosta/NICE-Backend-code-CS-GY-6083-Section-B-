package jtw.nice.service.impl;

import jtw.nice.entity.Payment;
import jtw.nice.mapper.InvoiceMapper;
import jtw.nice.mapper.PaymentMapper;
import jtw.nice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public PaymentServiceImpl(PaymentMapper paymentMapper, InvoiceMapper invoiceMapper) {
        this.paymentMapper = paymentMapper;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public int addPayment(Payment payment) {
        paymentMapper.addPayment(payment);
        return payment.getPaymentId();
    }

    @Override
    public void updatePayment(int paymentId, Payment payment) {
        int rowsAffected = paymentMapper.updatePayment(paymentId, payment);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update Payment with ID: " + paymentId);
        }
    }

    @Override
    public void deletePayment(int paymentId) {
        int rowsAffected = paymentMapper.deletePayment(paymentId);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to delete Payment with ID: " + paymentId);
        }
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        Payment payment = paymentMapper.getPaymentById(paymentId);
        if (payment == null) {
            throw new RuntimeException("Payment not found for ID: " + paymentId);
        }
        return payment;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentMapper.getAllPayments();
    }

    @Override
    @Transactional
    public Payment processPayment(int tripId, int groupId, BigDecimal paymentAmount, String paymentMethod) {
        // Retrieve invoice ID
        Integer invoiceId = invoiceMapper.getInvoiceIdByTripAndGroupId(tripId, groupId);
        if (invoiceId == null) {
            throw new IllegalArgumentException("Invoice not found for the given trip and group.");
        }

        // Update remaining amount in the invoice
        int rowsUpdated = invoiceMapper.updateRemainingAmount(tripId, groupId, paymentAmount);
        if (rowsUpdated == 0) {
            throw new IllegalArgumentException("Payment amount exceeds remaining amount in the invoice.");
        }

        // Create and insert payment record
        Payment payment = new Payment();
        payment.setPaymentAmount(paymentAmount);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentMethod(paymentMethod);
        payment.setTripId(tripId);
        payment.setGroupId(groupId);
        payment.setInvoiceId(invoiceId);

        int rowsInserted = paymentMapper.addPayment(payment);
        if (rowsInserted == 0) {
            throw new RuntimeException("Failed to insert payment record.");
        }

        return payment;
    }
}
