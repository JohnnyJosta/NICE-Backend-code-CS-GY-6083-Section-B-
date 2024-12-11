package jtw.nice.service.impl;

import jtw.nice.entity.Invoice;
import jtw.nice.entity.Trip;
import jtw.nice.mapper.InvoiceMapper;
import jtw.nice.mapper.TripInvoiceMapper;
import jtw.nice.mapper.TripMapper;
import jtw.nice.service.TripInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TripInvoiceServiceImpl implements TripInvoiceService {

    private final TripInvoiceMapper tripInvoiceMapper;
    private final InvoiceMapper invoiceMapper;
    private final TripMapper tripMapper;;

    @Autowired
    public TripInvoiceServiceImpl(TripInvoiceMapper tripInvoiceMapper, InvoiceMapper invoiceMapper, TripMapper tripMapper) {
        this.tripInvoiceMapper = tripInvoiceMapper;
        this.invoiceMapper = invoiceMapper;
        this.tripMapper = tripMapper;
    }

    @Override
    @Transactional
    public void generateInvoicesForTrip(int tripId) {
        // Step 1: Fetch room charges for the trip
        List<Map<String, Object>> roomCharges = tripInvoiceMapper.getRoomChargesByTripId(tripId);

        // Step 2: Fetch package charges for the trip
        List<Map<String, Object>> packageCharges = tripInvoiceMapper.getPackageChargesByTripId(tripId);

        // Step 3: Aggregate charges by group
        Map<Integer, BigDecimal> groupTotals = aggregateChargesByGroup(roomCharges, packageCharges, tripId);

        // Step 4: Create invoices for each group
        for (Map.Entry<Integer, BigDecimal> entry : groupTotals.entrySet()) {
            Integer groupId = entry.getKey();
            BigDecimal totalAmount = entry.getValue();

            // Create Invoice entity
            Invoice invoice = new Invoice();
            invoice.setChargeDate(LocalDate.now());
            invoice.setChargeAmount(totalAmount);
            invoice.setRemainingAmount(totalAmount); // Initially, the remaining amount is the total charge
            invoice.setTripId(tripId);
            invoice.setGroupId(groupId);

            // Save Invoice to the database
            invoiceMapper.addInvoice(invoice);
        }
    }

    /**
     * Aggregates room and package charges for each group and calculates the total charge amount.
     *
     * @param roomCharges The list of room charges.
     * @param packageCharges The list of package charges.
     * @param tripId The trip ID.
     * @return A map where the key is the group ID and the value is the total charge amount for that group.
     */
    private Map<Integer, BigDecimal> aggregateChargesByGroup(List<Map<String, Object>> roomCharges,
                                                             List<Map<String, Object>> packageCharges,
                                                             int tripId) {
        // Get trip details using TripMapper
        Trip trip = tripMapper.getTripById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip with ID " + tripId + " does not exist.");
        }
        LocalDateTime startDateTime = trip.getStartDateTime();
        LocalDateTime endDateTime = trip.getEndDateTime();

        // Calculate total trip days
        long tripDays = Duration.between(startDateTime, endDateTime).toDays();

        // Room Charges Aggregation
        Map<Integer, BigDecimal> groupTotals = new HashMap<>();
        for (Map<String, Object> roomCharge : roomCharges) {
            Integer groupId = (Integer) roomCharge.get("groupId");
            int roomId = (Integer) roomCharge.get("roomId");

            // Get room details by roomId
            Map<String, Object> roomDetails = tripInvoiceMapper.getRoomDetailsById(roomId);
            BigDecimal roomPrice = (BigDecimal) roomDetails.get("price");

            // Calculate total room cost for the trip
            BigDecimal totalRoomCost = roomPrice.multiply(BigDecimal.valueOf(tripDays));

            // Add to group total
            groupTotals.merge(groupId, totalRoomCost, BigDecimal::add);
        }

        // Package Charges Aggregation
        for (Map<String, Object> packageCharge : packageCharges) {
            Integer groupId = (Integer) packageCharge.get("groupId");
            int packageId = (Integer) packageCharge.get("packageId");

            // Get package details by packageId
            Map<String, Object> packageDetails = tripInvoiceMapper.getPackageDetailsById(packageId);
            BigDecimal packagePrice = (BigDecimal) packageDetails.get("pricePerPerson");

            // Add package cost to group total
            groupTotals.merge(groupId, packagePrice, BigDecimal::add);
        }

        return groupTotals;
    }
}
