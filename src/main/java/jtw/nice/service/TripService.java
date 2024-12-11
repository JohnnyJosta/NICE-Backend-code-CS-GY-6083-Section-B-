package jtw.nice.service;

import jtw.nice.entity.Stopover;
import jtw.nice.entity.Trip;
import jtw.nice.entity.dto.EmployeeStopoverDTO;
import jtw.nice.entity.dto.EmployeeTripDTO;
import jtw.nice.entity.dto.TripDetailsDTO;

import java.util.List;

public interface TripService {
    int addTrip(Trip trip);

    void updateTrip(int id, Trip trip);

    void deleteTrip(int id);

    Trip getTripById(int id);

    List<Trip> getAllTrips();

    int addStopover(Stopover stopover);

    void updateStopover(Stopover stopover);

    List<EmployeeStopoverDTO> getStopoversByTripId(int tripId);

    void deleteStopoversByTripId(int tripId);

    /**
     * Get trip details by trip ID.
     * @return TripDetailsDTO object containing trip details
     */
    List<TripDetailsDTO> getTripDetails();

    List<EmployeeTripDTO> getEmployeeTrips();
}
