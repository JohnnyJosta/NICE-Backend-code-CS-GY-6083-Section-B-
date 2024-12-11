package jtw.nice.mapper;

import jtw.nice.entity.Port;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortMapper {
    int addPort(Port port);

    int updatePort(@Param("id") int id, @Param("port") Port port);

    int deletePort(int portId);

    Port getPortById(int portId);

    List<Port> getAllPorts();
}
