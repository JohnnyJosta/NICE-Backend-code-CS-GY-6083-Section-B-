package jtw.nice.mapper;

import jtw.nice.entity.Entertainment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EntertainmentMapper {
    int addEntertainment(Entertainment entertainment);

    int updateEntertainment(@Param("id") int id, @Param("entertainment") Entertainment entertainment);

    int deleteEntertainment(int entertainmentId);

    Entertainment getEntertainmentById(int entertainmentId);

    List<Entertainment> getAllEntertainments();
}
