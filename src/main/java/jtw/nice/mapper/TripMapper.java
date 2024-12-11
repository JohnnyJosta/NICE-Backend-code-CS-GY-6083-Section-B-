package jtw.nice.mapper;

import jtw.nice.entity.Trip;
import jtw.nice.entity.Stopover;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TripMapper {
    int addTrip(Trip trip);

    int updateTrip(@Param("id") int id, @Param("trip") Trip trip);

    int deleteTrip(int tripId);

    Trip getTripById(int tripId);

    List<Trip> getAllTrips();

    int addStopover(Stopover stopover);

    int updateStopover(@Param("id") int id, @Param("stopover") Stopover stopover);

    List<Stopover> getStopoversByTripId(int tripId);

    int deleteStopoversByTripId(int tripId);

    Map<String, Object> getTripBasicDetails(int tripId);

    List<Map<String, Object>> getAllTripsBasicDetails();

    List<Map<String, Object>> getTripStops(int tripId);

    List<Map<String, Object>> getTripRestaurants(int tripId);

    List<Map<String, Object>> getTripEntertainments(int tripId);

    List<Map<String, Object>> getTripPackages(int tripId);
}
