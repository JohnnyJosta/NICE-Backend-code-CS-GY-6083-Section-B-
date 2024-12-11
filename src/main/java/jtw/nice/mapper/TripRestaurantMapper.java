package jtw.nice.mapper;

import jtw.nice.entity.TripRestaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripRestaurantMapper {

    int addTripRestaurant(TripRestaurant tripRestaurant);

    int updateTripRestaurant(@Param("tripId") int tripId,
                             @Param("restaurantId") int restaurantId,
                             @Param("quantity") int quantity);

    int deleteTripRestaurant(@Param("tripId") int tripId,
                             @Param("restaurantId") int restaurantId);

    List<TripRestaurant> getTripRestaurantsByTripId(int tripId);
}
