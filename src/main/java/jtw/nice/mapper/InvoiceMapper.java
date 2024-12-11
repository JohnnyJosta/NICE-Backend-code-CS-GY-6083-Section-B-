package jtw.nice.mapper;

import jtw.nice.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface InvoiceMapper {

    /**
     * Add a new Invoice.
     *
     * @param invoice The Invoice entity to add.
     * @return The number of rows affected.
     */
    int addInvoice(Invoice invoice);

    /**
     * Update an existing Invoice.
     *
     * @param invoiceId The ID of the Invoice to update.
     * @param invoice The Invoice entity with updated values.
     * @return The number of rows affected.
     */
    int updateInvoice(@Param("invoiceId") int invoiceId, @Param("invoice") Invoice invoice);

    /**
     * Delete an Invoice by ID.
     *
     * @param invoiceId The ID of the Invoice to delete.
     * @return The number of rows affected.
     */
    int deleteInvoice(int invoiceId);

    /**
     * Get an Invoice by ID.
     *
     * @param invoiceId The ID of the Invoice.
     * @return The Invoice entity.
     */
    Invoice getInvoiceById(int invoiceId);

    /**
     * Get all Invoices.
     *
     * @return A list of all Invoice entities.
     */
    List<Invoice> getAllInvoices();

    /**
     * Get all invoices associated with a specific group.
     *
     * @param groupId The group ID.
     * @return A list of invoices with detailed information.
     */
    List<Map<String, Object>> getInvoicesByGroupId(@Param("groupId") int groupId);

    /**
     * Update the remaining amount in the invoice.
     *
     * @param tripId The trip ID.
     * @param groupId The group ID.
     * @param paymentAmount The payment amount to subtract.
     * @return Number of rows affected.
     */
    int updateRemainingAmount(@Param("tripId") int tripId, @Param("groupId") int groupId, @Param("paymentAmount") BigDecimal paymentAmount);

    /**
     * Get the invoice ID by trip and group ID.
     *
     * @param tripId The trip ID.
     * @param groupId The group ID.
     * @return The invoice ID.
     */
    Integer getInvoiceIdByTripAndGroupId(@Param("tripId") int tripId, @Param("groupId") int groupId);
}

