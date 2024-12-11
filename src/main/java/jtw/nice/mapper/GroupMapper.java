package jtw.nice.mapper;

import jtw.nice.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupMapper {
    int addGroup(Group group);

    int updateGroup(@Param("id") int id, @Param("group") Group group);

    int deleteGroup(int groupId);

    Group getGroupById(int groupId);

    List<Group> getAllGroups();
}
