package jtw.nice.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class TripDetailsDTO {
    private Integer id;
    private String origin;
    private String destination;
    private String duration;
    private Double price;
    private String departureDate;
    private List<StopoverDTO> stops;
    private List<RestaurantDTO> restaurants;
    private List<PackageDTO> packages;
    private List<EntertainmentDTO> entertainments;
}
