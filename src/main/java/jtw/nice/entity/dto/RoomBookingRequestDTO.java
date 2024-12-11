package jtw.nice.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoomBookingRequestDTO {

    @NotNull(message = "Trip ID is required")
    private Integer tripId;

    @NotNull(message = "Rooms list cannot be null")
    private List<RoomRequestDTO> rooms;

    @Data
    public static class RoomRequestDTO {
        @NotBlank(message = "Room type is required")
        private String roomType;

        @NotBlank(message = "Location is required")
        private String location;

        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;
    }
}
