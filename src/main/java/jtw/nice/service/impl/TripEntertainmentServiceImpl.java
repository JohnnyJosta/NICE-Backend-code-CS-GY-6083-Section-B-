package jtw.nice.service.impl;

import jtw.nice.entity.TripEntertainment;
import jtw.nice.mapper.TripEntertainmentMapper;
import jtw.nice.service.TripEntertainmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripEntertainmentServiceImpl implements TripEntertainmentService {

    private final TripEntertainmentMapper tripEntertainmentMapper;

    @Autowired
    public TripEntertainmentServiceImpl(TripEntertainmentMapper tripEntertainmentMapper) {
        this.tripEntertainmentMapper = tripEntertainmentMapper;
    }

    @Override
    public int addTripEntertainment(TripEntertainment tripEntertainment) {
        tripEntertainmentMapper.addTripEntertainment(tripEntertainment);
        return tripEntertainment.getEntertainmentId();
    }

    @Override
    public void updateTripEntertainment(int tripId, int entertainmentId, int quantity) {
        int rowsAffected = tripEntertainmentMapper.updateTripEntertainment(tripId, entertainmentId, quantity);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update TripEntertainment for tripId: " + tripId +
                    " and entertainmentId: " + entertainmentId);
        }
    }

    @Override
    public void deleteTripEntertainment(int tripId, int entertainmentId) {
        int rowsAffected = tripEntertainmentMapper.deleteTripEntertainment(tripId, entertainmentId);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to delete TripEntertainment for tripId: " + tripId +
                    " and entertainmentId: " + entertainmentId);
        }
    }

    @Override
    public List<TripEntertainment> getTripEntertainmentsByTripId(int tripId) {
        List<TripEntertainment> entertainments = tripEntertainmentMapper.getTripEntertainmentsByTripId(tripId);
        if (entertainments == null || entertainments.isEmpty()) {
            throw new RuntimeException("No entertainments found for Trip ID: " + tripId);
        }
        return entertainments;
    }
}
