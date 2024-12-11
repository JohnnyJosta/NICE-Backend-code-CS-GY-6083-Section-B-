package jtw.nice.service;

import jtw.nice.entity.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceService {

    /**
     * Add a new Invoice.
     *
     * @param invoice The Invoice entity to add.
     * @return The ID of the newly created Invoice.
     */
    int addInvoice(Invoice invoice);

    /**
     * Update an existing Invoice.
     *
     * @param invoiceId The ID of the Invoice to update.
     * @param invoice The Invoice entity with updated values.
     */
    void updateInvoice(int invoiceId, Invoice invoice);

    /**
     * Delete an Invoice by ID.
     *
     * @param invoiceId The ID of the Invoice to delete.
     */
    void deleteInvoice(int invoiceId);

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
     * Get all invoices for a specific group.
     *
     * @param groupId The group ID.
     * @return A list of invoices with detailed information.
     */
    List<Map<String, Object>> getInvoicesByGroupId(int groupId);
}
