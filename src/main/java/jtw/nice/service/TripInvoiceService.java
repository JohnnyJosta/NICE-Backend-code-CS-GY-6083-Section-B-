package jtw.nice.service;

public interface TripInvoiceService {

    /**
     * Generate invoices for all groups in a specific trip.
     *
     * @param tripId The trip ID.
     */
    void generateInvoicesForTrip(int tripId);
}
