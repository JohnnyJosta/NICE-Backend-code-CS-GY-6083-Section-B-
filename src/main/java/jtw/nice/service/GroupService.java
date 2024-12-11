package jtw.nice.service;

import jtw.nice.entity.Group;
import jtw.nice.entity.Passenger;
import jtw.nice.entity.dto.GroupMemberDTO;

import java.util.List;

public interface GroupService {
    int addGroup(Group group);

    void updateGroup(int id, Group group);

    void deleteGroup(int id);

    Group getGroupById(int id);

    List<Group> getAllGroups();

    /**
     * Get group members by group id
     * @param groupId group id
     * @return list of group members
     */
    List<GroupMemberDTO> getGroupMembers(int groupId);

    /**
     * Add a new member to the group.
     *
     * @param passenger The passenger to be added.
     */
    void addMember(Passenger passenger);

}
