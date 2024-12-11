package jtw.nice.service;

import jtw.nice.entity.TripRestaurant;

import java.util.List;

public interface TripRestaurantService {

    int addTripRestaurant(TripRestaurant tripRestaurant);

    void updateTripRestaurant(int tripId, int restaurantId, int quantity);

    void deleteTripRestaurant(int tripId, int restaurantId);

    List<TripRestaurant> getTripRestaurantsByTripId(int tripId);
}
