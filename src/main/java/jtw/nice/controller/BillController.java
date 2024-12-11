package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.BillService;
import jtw.nice.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/view_bill")
public class BillController {

    private final BillService billService;
    private final UserHolder userHolder;

    @Autowired
    public BillController(BillService billService, UserHolder userHolder) {
        this.billService = billService;
        this.userHolder = userHolder;
    }

    /**
     * Get all charges for a specific trip for the current group.
     *
     * @param tripId The trip ID.
     * @return A list of charges.
     */
    @Operation(summary = "View bill", description = "Retrieve all charges for a specific trip for the current group")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> viewBill(@RequestParam int tripId) {
        Integer groupId = userHolder.getGroupId();

        if (groupId == null || groupId == 0) {
            return ResponseEntity.status(403)
                    .body(ApiResponse.fail("User does not belong to a valid group."));
        }

        List<Map<String, Object>> billDetails = billService.getBillDetailsByTripAndGroupId(tripId, groupId);
        return ResponseEntity.ok(ApiResponse.success("Bill details fetched successfully", billDetails));
    }
}

