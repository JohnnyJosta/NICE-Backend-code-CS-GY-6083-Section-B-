package jtw.nice.service.impl;

import jtw.nice.entity.Port;
import jtw.nice.mapper.PortMapper;
import jtw.nice.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortServiceImpl implements PortService {

    private final PortMapper portMapper;

    @Autowired
    public PortServiceImpl(PortMapper portMapper) {
        this.portMapper = portMapper;
    }

    @Override
    public int addPort(Port port) {
        portMapper.addPort(port);
        return port.getPortId();
    }

    @Override
    public void updatePort(int id, Port port) {
        int rows = portMapper.updatePort(id, port);
        if (rows == 0) {
            throw new RuntimeException("Failed to update port with ID: " + id);
        }
    }

    @Override
    public void deletePort(int id) {
        int rows = portMapper.deletePort(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete port with ID: " + id);
        }
    }

    @Override
    public Port getPortById(int id) {
        Port port = portMapper.getPortById(id);
        if (port == null) {
            throw new RuntimeException("Port not found for ID: " + id);
        }
        return port;
    }

    @Override
    public List<Port> getAllPorts() {
        return portMapper.getAllPorts();
    }
}
