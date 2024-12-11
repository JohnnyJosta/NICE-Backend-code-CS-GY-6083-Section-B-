package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("jtw_stateroom")
public class Stateroom {
    @TableId(value = "room_id", type = IdType.AUTO)
    private Integer roomId;

    @TableField("room_type")
    private String roomType;

    @TableField("room_size")
    private BigDecimal roomSize;

    @TableField("bed_number")
    private Integer bedNumber;

    @TableField("bathroom_number")
    private BigDecimal bathroomNumber;

    @TableField("balcony_number")
    private Integer balconyNumber;

    @TableField("price")
    private BigDecimal price;

    @TableField("location")
    private String location;
}
