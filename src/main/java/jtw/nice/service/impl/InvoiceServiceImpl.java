package jtw.nice.service.impl;

import jtw.nice.entity.Invoice;
import jtw.nice.mapper.InvoiceMapper;
import jtw.nice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceServiceImpl(InvoiceMapper invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public int addInvoice(Invoice invoice) {
        invoiceMapper.addInvoice(invoice);
        return invoice.getInvoiceId();
    }

    @Override
    public void updateInvoice(int invoiceId, Invoice invoice) {
        int rowsAffected = invoiceMapper.updateInvoice(invoiceId, invoice);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update Invoice with ID: " + invoiceId);
        }
    }

    @Override
    public void deleteInvoice(int invoiceId) {
        int rowsAffected = invoiceMapper.deleteInvoice(invoiceId);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to delete Invoice with ID: " + invoiceId);
        }
    }

    @Override
    public Invoice getInvoiceById(int invoiceId) {
        Invoice invoice = invoiceMapper.getInvoiceById(invoiceId);
        if (invoice == null) {
            throw new RuntimeException("Invoice not found for ID: " + invoiceId);
        }
        return invoice;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceMapper.getAllInvoices();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getInvoicesByGroupId(int groupId) {
        return invoiceMapper.getInvoicesByGroupId(groupId);
    }
}

