package jtw.nice.service;

import jtw.nice.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    int addRestaurant(Restaurant restaurant);

    void updateRestaurant(int id, Restaurant restaurant);

    void deleteRestaurant(int id);

    Restaurant getRestaurantById(int id);

    List<Restaurant> getAllRestaurants();
}
