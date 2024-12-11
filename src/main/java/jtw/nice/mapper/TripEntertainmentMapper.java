package jtw.nice.mapper;

import jtw.nice.entity.TripEntertainment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripEntertainmentMapper {

    int addTripEntertainment(TripEntertainment tripEntertainment);

    int updateTripEntertainment(@Param("tripId") int tripId,
                                @Param("entertainmentId") int entertainmentId,
                                @Param("quantity") int quantity);

    int deleteTripEntertainment(@Param("tripId") int tripId,
                                @Param("entertainmentId") int entertainmentId);

    List<TripEntertainment> getTripEntertainmentsByTripId(int tripId);
}

