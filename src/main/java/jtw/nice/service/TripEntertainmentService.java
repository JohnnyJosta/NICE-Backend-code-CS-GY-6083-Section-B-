package jtw.nice.service;

import jtw.nice.entity.TripEntertainment;

import java.util.List;

public interface TripEntertainmentService {

    int addTripEntertainment(TripEntertainment tripEntertainment);

    void updateTripEntertainment(int tripId, int entertainmentId, int quantity);

    void deleteTripEntertainment(int tripId, int entertainmentId);

    List<TripEntertainment> getTripEntertainmentsByTripId(int tripId);
}
