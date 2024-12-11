package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("jtw_stopover")
public class Stopover {

    @TableId(value = "stopover_id", type = IdType.AUTO)
    private Integer stopoverId;

    @TableField("trip_id")
    @NotNull(message = "Trip ID cannot be null")
    private Integer tripId;

    @TableField("port_id")
    @NotNull(message = "Port ID cannot be null")
    private Integer portId;

    @TableField("arrival_time")
    @NotNull(message = "Arrival Time cannot be null")
    private LocalDateTime arrivalTime;

    @TableField("departure_time")
    @NotNull(message = "Departure Time cannot be null")
    private LocalDateTime departureTime;

    @TableField("notes")
    private String notes;
}
