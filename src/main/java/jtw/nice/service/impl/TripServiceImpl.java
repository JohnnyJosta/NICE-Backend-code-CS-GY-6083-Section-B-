package jtw.nice.service.impl;

import jtw.nice.entity.Port;
import jtw.nice.entity.Stopover;
import jtw.nice.entity.Trip;
import jtw.nice.entity.dto.*;
import jtw.nice.mapper.PortMapper;
import jtw.nice.mapper.TripMapper;
import jtw.nice.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private final TripMapper tripMapper;
    private final PortMapper portMapper;

    @Autowired
    public TripServiceImpl(TripMapper tripMapper, PortMapper portMapper) {
        this.tripMapper = tripMapper;
        this.portMapper = portMapper;
    }

    @Override
    @Transactional
    public int addTrip(Trip trip) {
        // Validate start and end dates
        if (trip.getStartDateTime() == null || trip.getEndDateTime() == null) {
            throw new IllegalArgumentException("Start and end dates must not be null.");
        }
        if (trip.getEndDateTime().isBefore(trip.getStartDateTime())) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        // Calculate total nights
        long totalNights = ChronoUnit.DAYS.between(
                trip.getStartDateTime().toLocalDate(),
                trip.getEndDateTime().toLocalDate()
        );

        // Set total nights to the Trip object
        trip.setTotalNight((int) totalNights);

        // Add trip to the database
        tripMapper.addTrip(trip);
        return trip.getTripId();
    }

    @Override
    public void updateTrip(int id, Trip trip) {
        // 获取当前的 Trip 数据
        Trip existingTrip = tripMapper.getTripById(id);
        if (existingTrip == null) {
            throw new RuntimeException("Trip not found for ID: " + id);
        }

        // 检查并更新开始和结束时间
        LocalDateTime startDateTime = trip.getStartDateTime() != null
                ? trip.getStartDateTime()
                : existingTrip.getStartDateTime();

        LocalDateTime endDateTime = trip.getEndDateTime() != null
                ? trip.getEndDateTime()
                : existingTrip.getEndDateTime();

        // 验证时间顺序
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        // 计算新的 totalNight
        int totalNights = (int) ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());

        // 更新 totalNight 字段
        trip.setTotalNight(totalNights);

        // 调用更新方法
        int rows = tripMapper.updateTrip(id, trip);
        if (rows == 0) {
            throw new RuntimeException("Failed to update trip with ID: " + id);
        }
    }

    @Override
    public void deleteTrip(int id) {
        int rows = tripMapper.deleteTrip(id);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete trip with ID: " + id);
        }
    }

    @Override
    public Trip getTripById(int id) {
        Trip trip = tripMapper.getTripById(id);
        if (trip == null) {
            throw new RuntimeException("Trip not found for ID: " + id);
        }
        return trip;
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripMapper.getAllTrips();
    }

    @Override
    public int addStopover(Stopover stopover) {
        tripMapper.addStopover(stopover);
        return stopover.getStopoverId();
    }

    @Override
    public void updateStopover(Stopover stopover) {
        int id = stopover.getStopoverId();
        int rows = tripMapper.updateStopover(id, stopover);
        if (rows == 0) {
            throw new RuntimeException("Failed to update stopover with ID: " + id);
        }
    }

    @Override
    public List<EmployeeStopoverDTO> getStopoversByTripId(int tripId) {
        // 查询 Stopover 数据
        List<Stopover> stopovers = tripMapper.getStopoversByTripId(tripId);
        if (stopovers == null || stopovers.isEmpty()) {
            throw new RuntimeException("No stopovers found for Trip ID: " + tripId);
        }

        // 转换为 StopoverDTO
        return stopovers.stream()
                .map(stopover -> {
                    // 查询 Port 名称
                    String portName = portMapper.getPortById(stopover.getPortId()).getPortName();
                    return new EmployeeStopoverDTO(
                            stopover.getStopoverId(),
                            portName,
                            stopover.getArrivalTime(),
                            stopover.getDepartureTime()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStopoversByTripId(int tripId) {
        int rows = tripMapper.deleteStopoversByTripId(tripId);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete stopovers for Trip ID: " + tripId);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TripDetailsDTO> getTripDetails() {
        // 获取所有旅程的基本信息
        List<Map<String, Object>> allTrips = tripMapper.getAllTripsBasicDetails();
        System.out.println("Get all trips");
        System.out.println(allTrips);
        // 遍历所有旅程并构建 TripDetailsDTO 列表
        return allTrips.stream().map(basicDetails -> {
            int tripId = (Integer) basicDetails.get("id");

            // 构建基本信息
            TripDetailsDTO tripDetails = new TripDetailsDTO();
            tripDetails.setId(tripId);
            tripDetails.setOrigin((String) basicDetails.get("origin"));
            tripDetails.setDestination((String) basicDetails.get("destination"));
            tripDetails.setDuration(basicDetails.get("duration") + " days");
            // tripDetails.setPrice((Double) basicDetails.get("price"));
            java.sql.Date departureDate = (java.sql.Date) basicDetails.get("departureDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tripDetails.setDepartureDate(dateFormat.format(departureDate));

            // 获取经停站
            List<Map<String, Object>> stops = tripMapper.getTripStops(tripId);
            List<StopoverDTO> stopoverDTOs = stops.stream()
                    .map(stop -> {
                        StopoverDTO dto = new StopoverDTO();
                        dto.setPortName((String) stop.get("portName"));

                        // 处理 arrivalTime
                        java.time.LocalDateTime arrivalDateTime = (java.time.LocalDateTime) stop.get("arrivalTime");
                        dto.setArrivalTime(arrivalDateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                        // 处理 departureTime
                        java.time.LocalDateTime departureDateTime = (java.time.LocalDateTime) stop.get("departureTime");
                        dto.setDepartureTime(departureDateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                        return dto;
                    })
                    .collect(Collectors.toList());
            tripDetails.setStops(stopoverDTOs);

            // 获取餐厅
            List<Map<String, Object>> restaurants = tripMapper.getTripRestaurants(tripId);
            List<RestaurantDTO> restaurantDTOs = restaurants.stream()
                    .map(restaurant -> {
                        RestaurantDTO dto = new RestaurantDTO();
                        dto.setName((String) restaurant.get("name"));
                        dto.setType((String) restaurant.get("type"));
                        dto.setServeTime((String) restaurant.get("serveTime"));
                        dto.setLocation((String) restaurant.get("location"));
                        return dto;
                    })
                    .collect(Collectors.toList());
            tripDetails.setRestaurants(restaurantDTOs);

            // 获取娱乐设施
            List<Map<String, Object>> entertainments = tripMapper.getTripEntertainments(tripId);
            List<EntertainmentDTO> entertainmentDTOs = entertainments.stream()
                    .map(entertainment -> {
                        EntertainmentDTO dto = new EntertainmentDTO();
                        dto.setName((String) entertainment.get("name"));
                        dto.setLocation((String) entertainment.get("location"));
                        dto.setQuantity((Integer) entertainment.get("quantity"));
                        return dto;
                    })
                    .collect(Collectors.toList());
            tripDetails.setEntertainments(entertainmentDTOs);

            // 获取套餐
            List<Map<String, Object>> packages = tripMapper.getTripPackages(tripId);
            List<PackageDTO> packageDTOs = packages.stream()
                    .map(pack -> {
                        PackageDTO dto = new PackageDTO();
                        dto.setName((String) pack.get("name"));
                        dto.setPrice(((BigDecimal) pack.get("price")));
                        dto.setChargeMethod((String) pack.get("chargeMethod"));
                        return dto;
                    })
                    .collect(Collectors.toList());
            tripDetails.setPackages(packageDTOs);
            System.out.println(tripDetails);
            return tripDetails;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeTripDTO> getEmployeeTrips() {
        // Retrieve all trips from the Trip table
        List<Trip> trips = tripMapper.getAllTrips();

        // Map Trip to EmployeeTripDTO
        return trips.stream().map(trip -> {
            // Fetch port names based on port IDs
            Port startPort = portMapper.getPortById(trip.getStartPortId());
            Port endPort = portMapper.getPortById(trip.getEndPortId());

            if (startPort == null || endPort == null) {
                throw new RuntimeException("Port not found for trip ID: " + trip.getTripId());
            }

            // Map Trip and Ports to EmployeeTripDTO
            return new EmployeeTripDTO(
                    trip.getTripId(),
                    startPort.getPortName(),
                    endPort.getPortName(),
                    trip.getStartDateTime(),
                    trip.getEndDateTime()
            );
        }).collect(Collectors.toList());
    }
}
