package jtw.nice.service.impl;

import jtw.nice.entity.Passenger;
import jtw.nice.entity.User;
import jtw.nice.mapper.PassengerMapper;
import jtw.nice.service.PassengerService;
import jtw.nice.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerMapper passengerMapper;

    @Autowired
    public PassengerServiceImpl(PassengerMapper passengerMapper) {
        this.passengerMapper = passengerMapper;
    }

    @Override
    public int addPassenger(Passenger passenger) {
        passengerMapper.addPassenger(passenger);
        return passenger.getPassengerId();
    }

    @Override
    public void updatePassenger(int id, Passenger passenger) {
        int rows = passengerMapper.updatePassenger(id, passenger);
        if (rows == 0) {
            throw new RuntimeException("Failed to update passenger with ID: " + id);
        }
    }

    @Override
    public void deletePassenger(int id) {
        int rows = passengerMapper.deletePassenger(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete passenger with ID: " + id);
        }
    }

    @Override
    public Passenger getPassengerById(int id) {
        Passenger passenger = passengerMapper.getPassengerById(id);
        if (passenger == null) {
            throw new RuntimeException("Passenger not found for ID: " + id);
        }
        return passenger;
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerMapper.getAllPassengers();
    }

    @Override
    public void removePassengerFromGroup(int passengerId) {
        // Execute the update operation
        int rowsAffected = passengerMapper.removePassengerFromGroup(passengerId);

        // Handle cases where the passenger ID does not exist
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Passenger with ID " + passengerId + " does not exist.");
        }
    }
}
