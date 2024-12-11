package jtw.nice.service;

import java.util.List;
import java.util.Map;

public interface BillService {

    /**
     * Get all charges for a specific trip and group.
     *
     * @param tripId The trip ID.
     * @param groupId The group ID.
     * @return A combined list of package and room charges.
     */
    List<Map<String, Object>> getBillDetailsByTripAndGroupId(int tripId, int groupId);
}
