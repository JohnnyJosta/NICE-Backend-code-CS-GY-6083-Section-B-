package jtw.nice.service;

import jtw.nice.entity.Port;

import java.util.List;

public interface PortService {
    int addPort(Port port);

    void updatePort(int id, Port port);

    void deletePort(int id);

    Port getPortById(int id);

    List<Port> getAllPorts();
}
