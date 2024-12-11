package jtw.nice.mapper;

import jtw.nice.entity.Passenger;
import jtw.nice.entity.dto.GroupMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PassengerMapper {
    int addPassenger(Passenger passenger);

    int updatePassenger(@Param("id") int id, @Param("passenger") Passenger passenger);

    int deletePassenger(int passengerId);

    Passenger getPassengerById(int passengerId);

    List<Passenger> getAllPassengers();

    /**
     * Search passengers by group ID
     * @param groupId group ID
     * @return list of passengers
     */
    List<GroupMemberDTO> getPassengersByGroupId(@Param("groupId") int groupId);

    /**
     * Remove a passenger from the group by updating group_id to 0.
     *
     * @param passengerId the ID of the passenger to be removed
     * @return the number of rows affected
     */
    int removePassengerFromGroup(@Param("passengerId") int passengerId);
}
