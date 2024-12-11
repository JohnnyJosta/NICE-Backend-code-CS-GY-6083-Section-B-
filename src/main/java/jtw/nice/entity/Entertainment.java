package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("jtw_entertainment")
public class Entertainment {
    @TableId(value = "entertainment_id", type = IdType.AUTO)
    private Integer entertainmentId;

    @TableField("entertainment_name")
    @NotBlank(message = "Entertainment Name cannot be empty")
    private String entertainmentName;

    @TableField("number_of_unit")
    @NotNull(message = "Number of Unit cannot be empty")
    private Integer numberOfUnit;

    @TableField("floor")
    @NotBlank(message = "Floor cannot be empty")
    private String floor;
}

