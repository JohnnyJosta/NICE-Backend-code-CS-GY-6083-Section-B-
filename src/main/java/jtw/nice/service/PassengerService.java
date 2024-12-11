package jtw.nice.service;

import jtw.nice.entity.Passenger;

import java.util.List;

public interface PassengerService {
    int addPassenger(Passenger passenger);

    void updatePassenger(int id, Passenger passenger);

    void deletePassenger(int id);

    Passenger getPassengerById(int id);

    List<Passenger> getAllPassengers();

    /**
     * Remove a passenger from the group.
     *
     * @param passengerId the ID of the passenger to be removed
     */
    void removePassengerFromGroup(int passengerId);
}
