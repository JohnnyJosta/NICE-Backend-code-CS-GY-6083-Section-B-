package jtw.nice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TripInvoiceMapper {

    /**
     * Get room charges for a specific trip.
     *
     * @param tripId The trip ID.
     * @return List of room charges with group ID, room ID, and charge date.
     */
    List<Map<String, Object>> getRoomChargesByTripId(@Param("tripId") int tripId);

    /**
     * Get package charges for a specific trip.
     *
     * @param tripId The trip ID.
     * @return List of package charges with group ID, package ID, and charge date.
     */
    List<Map<String, Object>> getPackageChargesByTripId(@Param("tripId") int tripId);

    /**
     * Get details of a specific room by its ID.
     *
     * @param roomId The room ID.
     * @return Room details including type and price.
     */
    Map<String, Object> getRoomDetailsById(@Param("roomId") int roomId);

    /**
     * Get details of a specific package by its ID.
     *
     * @param packageId The package ID.
     * @return Package details including price and charge period.
     */
    Map<String, Object> getPackageDetailsById(@Param("packageId") int packageId);
}
