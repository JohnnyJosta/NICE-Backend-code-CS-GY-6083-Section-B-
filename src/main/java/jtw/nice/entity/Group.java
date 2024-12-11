package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("jtw_group")
public class Group {

    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    @TableField("group_name")
    private String groupName;

    @TableField("passenger_number")
    private Integer passengerNumber;

    @TableField("trip_id")
    private Integer tripId;
}
