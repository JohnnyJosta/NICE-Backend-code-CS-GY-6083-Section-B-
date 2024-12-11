package jtw.nice.service.impl;

import jtw.nice.entity.Entertainment;
import jtw.nice.mapper.EntertainmentMapper;
import jtw.nice.service.EntertainmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntertainmentServiceImpl implements EntertainmentService {

    private final EntertainmentMapper entertainmentMapper;

    @Autowired
    public EntertainmentServiceImpl(EntertainmentMapper entertainmentMapper) {
        this.entertainmentMapper = entertainmentMapper;
    }

    @Override
    public int addEntertainment(Entertainment entertainment) {
        entertainmentMapper.addEntertainment(entertainment);
        return entertainment.getEntertainmentId();
    }

    @Override
    public void updateEntertainment(int id, Entertainment entertainment) {
        int rows = entertainmentMapper.updateEntertainment(id, entertainment);
        if (rows == 0) {
            throw new RuntimeException("Failed to update entertainment with ID: " + id);
        }
    }

    @Override
    public void deleteEntertainment(int id) {
        int rows = entertainmentMapper.deleteEntertainment(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete entertainment with ID: " + id);
        }
    }

    @Override
    public Entertainment getEntertainmentById(int id) {
        Entertainment entertainment = entertainmentMapper.getEntertainmentById(id);
        if (entertainment == null) {
            throw new RuntimeException("Entertainment not found for ID: " + id);
        }
        return entertainment;
    }

    @Override
    public List<Entertainment> getAllEntertainments() {
        return entertainmentMapper.getAllEntertainments();
    }
}
