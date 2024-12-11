package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("jtw_port")
public class Port {
    @TableId(value = "port_id", type = IdType.AUTO)
    private Integer portId;

    @TableField("port_name")
    @NotBlank(message = "Port Name cannot be empty")
    private String portName;

    @TableField("port_state")
    @NotBlank(message = "Port State cannot be empty")
    private String portState;

    @TableField("port_nation")
    @NotBlank(message = "Port Nation cannot be empty")
    private String portNation;

    @TableField("port_address")
    @NotBlank(message = "Port Address cannot be empty")
    private String portAddress;

    @TableField("nearest_airport")
    @NotBlank(message = "Nearest Airport cannot be empty")
    private String nearestAirport;

    @TableField("parking_spot")
    @NotNull(message = "Parking Spot cannot be empty")
    private Integer parkingSpot;
}

