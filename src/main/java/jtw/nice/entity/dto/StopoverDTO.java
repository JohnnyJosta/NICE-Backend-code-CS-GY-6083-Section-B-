package jtw.nice.entity.dto;

import lombok.Data;

@Data
public class StopoverDTO {
    private String portName;
    private String arrivalTime;
    private String departureTime;
}
