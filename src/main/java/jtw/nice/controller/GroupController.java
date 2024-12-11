package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jtw.nice.entity.Group;
import jtw.nice.entity.Passenger;
import jtw.nice.entity.dto.GroupMemberDTO;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.GroupService;
import jtw.nice.service.PassengerService;
import jtw.nice.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class GroupController {

    private final GroupService groupService;
    private final PassengerService passengerService;
    private final UserHolder userHolder;

    @Autowired
    public GroupController(GroupService groupService, PassengerService passengerService, UserHolder userHolder) {
        this.groupService = groupService;
        this.passengerService = passengerService;
        this.userHolder = userHolder;
    }

    /**
     * Get all groups
     * @return
     */
    @Operation(summary = "Get all groups", description = "Retrieve all groups")
    @GetMapping("get_groups")
    public ResponseEntity<ApiResponse<List<Group>>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all groups", groups));
    }

    /**
     * Get group by ID
     * @return
     */
    @Operation(summary = "Get group by ID", description = "Retrieve a group by its ID")
    @GetMapping("get_group/{id}")
    public ResponseEntity<ApiResponse<Group>> getGroupById(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        Group group = groupService.getGroupById(id);
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched group", group));
    }

    /**
     * Add a new group
     * @return
     */
    @Operation(summary = "Add a new group", description = "Create a new group record")
    @PostMapping("/add_group")
    public ResponseEntity<ApiResponse<Integer>> addGroup(@RequestBody @Valid Group group) {
        int id = groupService.addGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Group added successfully", id));
    }

    /**
     * Update a group
     * @return
     */
    @Operation(summary = "Update a group", description = "Update an existing group record")
    @PutMapping("/update_group/{id}")
    public ResponseEntity<ApiResponse<Void>> updateGroup(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id,
            @RequestBody @Valid Group group) {
        groupService.updateGroup(id, group);
        return ResponseEntity.ok(ApiResponse.success("Group updated successfully"));
    }

    /**
     * Delete a group
     * @return
     */
    @Operation(summary = "Delete a group", description = "Remove a group record by ID")
    @DeleteMapping("/delete_group/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(
            @PathVariable @Min(value = 1, message = "Minimum ID is 1") int id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok(ApiResponse.success("Group deleted successfully"));
    }

    /**
     * Get list of the group members
     * @return List of group members
     */
    @Operation(summary = "Get group members", description = "Retrieve all members of the group associated with the current user")
    @GetMapping("/get_group_members")
    public ResponseEntity<ApiResponse<List<GroupMemberDTO>>> getGroupMembers() {
        Integer groupId = userHolder.getGroupId();

        if (groupId == null || groupId == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.fail("User does not belong to a valid group."));
        }

        List<GroupMemberDTO> members = groupService.getGroupMembers(groupId);
        return ResponseEntity.ok(ApiResponse.success("Group members fetched successfully", members));
    }


    /**
     * Remove a passenger from the group.
     *
     * @param passengerId the ID of the passenger to be removed
     * @return success message
     */
    @Operation(summary = "Remove passenger from group",
            description = "Update the passenger's group_id to 0 to remove them from the group")
    @PutMapping("/delete_member/{passengerId}")
    public ResponseEntity<ApiResponse<Void>> removePassengerFromGroup(
            @PathVariable @Min(value = 1, message = "Passenger ID must be greater than 0") int passengerId) {
        // Call service layer to remove the passenger from the group
        passengerService.removePassengerFromGroup(passengerId);

        // Return success response
        return ResponseEntity.ok(ApiResponse.success("Passenger successfully removed from the group"));
    }

    /**
     * Add a new member to the group.
     *
     * @param passenger The passenger object containing member details.
     * @return Success message upon successful addition.
     */
    @Operation(summary = "Add a new group member", description = "Insert a new passenger into the current user's group")
    @PostMapping("/add_member")
    public ResponseEntity<ApiResponse<Void>> addMember(@RequestBody Passenger passenger) {
        Integer groupId = userHolder.getGroupId();

        if (groupId == null || groupId == 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.fail("User does not belong to a valid group."));
        }

        // Set groupId in passenger entity
        passenger.setGroupId(groupId);

        // Add the passenger to the group
        groupService.addMember(passenger);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Group member added successfully"));
    }

}
