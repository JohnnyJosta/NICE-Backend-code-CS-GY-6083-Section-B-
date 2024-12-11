package jtw.nice.service;

import jtw.nice.entity.Entertainment;

import java.util.List;

public interface EntertainmentService {
    int addEntertainment(Entertainment entertainment);

    void updateEntertainment(int id, Entertainment entertainment);

    void deleteEntertainment(int id);

    Entertainment getEntertainmentById(int id);

    List<Entertainment> getAllEntertainments();
}
