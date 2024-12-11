package jtw.nice.service.impl;

import jtw.nice.entity.Restaurant;
import jtw.nice.mapper.RestaurantMapper;
import jtw.nice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public int addRestaurant(Restaurant restaurant) {
        System.out.println(restaurant == null);
        System.out.println(restaurant.getRestaurantName());
        restaurantMapper.addRestaurant(restaurant);
        return restaurant.getRestaurantId();
    }

    @Override
    public void updateRestaurant(int id, Restaurant restaurant) {
        int rows = restaurantMapper.updateRestaurant(id, restaurant);
        if (rows == 0) {
            throw new RuntimeException("Failed to update restaurant with ID: " + id);
        }
    }

    @Override
    public void deleteRestaurant(int id) {
        int rows = restaurantMapper.deleteRestaurant(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete restaurant with ID: " + id);
        }
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        Restaurant restaurant = restaurantMapper.getRestaurantById(id);
        if (restaurant == null) {
            throw new RuntimeException("Restaurant not found for ID: " + id);
        }
        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantMapper.getAllRestaurants();
    }
}
