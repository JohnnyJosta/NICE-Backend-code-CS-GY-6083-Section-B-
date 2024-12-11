package jtw.nice.mapper;

import jtw.nice.entity.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    int addRestaurant(Restaurant restaurant);

    int updateRestaurant(@Param("id") int id, @Param("restaurant") Restaurant restaurant);

    int deleteRestaurant(int restaurantId);

    Restaurant getRestaurantById(int restaurantId);

    List<Restaurant> getAllRestaurants();
}
