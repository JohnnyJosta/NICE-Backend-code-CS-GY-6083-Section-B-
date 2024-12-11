package jtw.nice.service.impl;

import jtw.nice.entity.TripRestaurant;
import jtw.nice.mapper.TripRestaurantMapper;
import jtw.nice.service.TripRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripRestaurantServiceImpl implements TripRestaurantService {

    private final TripRestaurantMapper tripRestaurantMapper;

    @Autowired
    public TripRestaurantServiceImpl(TripRestaurantMapper tripRestaurantMapper) {
        this.tripRestaurantMapper = tripRestaurantMapper;
    }

    @Override
    public int addTripRestaurant(TripRestaurant tripRestaurant) {
        tripRestaurantMapper.addTripRestaurant(tripRestaurant);
        return tripRestaurant.getRestaurantId();
    }

    @Override
    public void updateTripRestaurant(int tripId, int restaurantId, int quantity) {
        int rowsAffected = tripRestaurantMapper.updateTripRestaurant(tripId, restaurantId, quantity);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update TripRestaurant for tripId: " + tripId +
                    " and restaurantId: " + restaurantId);
        }
    }

    @Override
    public void deleteTripRestaurant(int tripId, int restaurantId) {
        int rowsAffected = tripRestaurantMapper.deleteTripRestaurant(tripId, restaurantId);
        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to delete TripRestaurant for tripId: " + tripId +
                    " and restaurantId: " + restaurantId);
        }
    }

    @Override
    public List<TripRestaurant> getTripRestaurantsByTripId(int tripId) {
        List<TripRestaurant> restaurants = tripRestaurantMapper.getTripRestaurantsByTripId(tripId);
        if (restaurants == null || restaurants.isEmpty()) {
            throw new RuntimeException("No restaurants found for Trip ID: " + tripId);
        }
        return restaurants;
    }
}
