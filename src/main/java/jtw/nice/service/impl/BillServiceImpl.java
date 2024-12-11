package jtw.nice.service.impl;

import jtw.nice.mapper.PackageChargeMapper;
import jtw.nice.mapper.RoomChargeMapper;
import jtw.nice.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {

    private final PackageChargeMapper packageChargeMapper;
    private final RoomChargeMapper roomChargeMapper;

    @Autowired
    public BillServiceImpl(PackageChargeMapper packageChargeMapper, RoomChargeMapper roomChargeMapper) {
        this.packageChargeMapper = packageChargeMapper;
        this.roomChargeMapper = roomChargeMapper;
    }

    @Override
    public List<Map<String, Object>> getBillDetailsByTripAndGroupId(int tripId, int groupId) {
        List<Map<String, Object>> packageCharges = packageChargeMapper.getPackageChargesByTripAndGroupId(tripId, groupId);
        List<Map<String, Object>> roomCharges = roomChargeMapper.getRoomChargesByTripAndGroupId(tripId, groupId);

        // Combine results from both tables
        List<Map<String, Object>> allCharges = new ArrayList<>();
        allCharges.addAll(packageCharges);
        allCharges.addAll(roomCharges);

        // Sort by time
        allCharges.sort((c1, c2) -> ((String) c1.get("time")).compareTo((String) c2.get("time")));

        return allCharges;
    }
}

