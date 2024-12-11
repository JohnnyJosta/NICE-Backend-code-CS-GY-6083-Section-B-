package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
@TableName("jtw_restaurant")
public class Restaurant {
    @TableId(value = "restaurant_id", type = IdType.AUTO)
    private Integer restaurantId;

    @TableField("restaurant_name")
    private String restaurantName;

    @TableField("meal_service")
    private String mealService;

    @TableField("start_time")
    private LocalTime startTime;

    @TableField("end_time")
    private LocalTime endTime;

    @TableField("floor")
    private String floor;
}

