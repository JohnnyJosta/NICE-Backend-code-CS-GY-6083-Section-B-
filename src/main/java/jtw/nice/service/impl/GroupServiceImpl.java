package jtw.nice.service.impl;

import jtw.nice.entity.Group;
import jtw.nice.entity.Passenger;
import jtw.nice.entity.dto.GroupMemberDTO;
import jtw.nice.mapper.GroupMapper;
import jtw.nice.mapper.PassengerMapper;
import jtw.nice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;
    private final PassengerMapper passengerMapper;

    @Autowired
    public GroupServiceImpl(GroupMapper groupMapper, PassengerMapper passengerMapper) {
        this.groupMapper = groupMapper;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public int addGroup(Group group) {
        groupMapper.addGroup(group);
        return group.getGroupId();
    }

    @Override
    public void updateGroup(int id, Group group) {
        int rows = groupMapper.updateGroup(id, group);
        if (rows == 0) {
            throw new RuntimeException("Failed to update group with ID: " + id);
        }
    }

    @Override
    public void deleteGroup(int id) {
        int rows = groupMapper.deleteGroup(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete group with ID: " + id);
        }
    }

    @Override
    public Group getGroupById(int id) {
        Group group = groupMapper.getGroupById(id);
        if (group == null) {
            throw new RuntimeException("Group not found for ID: " + id);
        }
        return group;
    }

    @Override
    public List<Group> getAllGroups() {
        return groupMapper.getAllGroups();
    }

    @Override
    public List<GroupMemberDTO> getGroupMembers(int groupId) {
        // 从 Mapper 获取 Passenger 列表
        List<GroupMemberDTO> passengers = passengerMapper.getPassengersByGroupId(groupId);

        // 转换为 DTO
        return passengers.stream().map(passenger -> {
            GroupMemberDTO dto = new GroupMemberDTO();
            dto.setId(passenger.getId());
            dto.setName(passenger.getName());
            dto.setBirthDate(passenger.getBirthDate());
            dto.setPhone(passenger.getPhone());
            dto.setEmail(passenger.getEmail());
            dto.setGender(passenger.getGender());
            dto.setAddress(passenger.getAddress());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void addMember(Passenger passenger) {
        passengerMapper.addPassenger(passenger);
    }
}
