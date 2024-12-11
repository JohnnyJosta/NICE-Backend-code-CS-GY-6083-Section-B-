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
@TableName("jtw_package")
public class Package {
    @TableId(value = "package_id", type = IdType.AUTO)
    private Integer packageId;

    @TableField("package_name")
    @NotBlank(message = "Package Name cannot be empty")
    private String packageName;

    @TableField("price_per_person")
    @NotNull(message = "Price per Person cannot be empty")
    private BigDecimal pricePerPerson;

    @TableField("charge_period")
    @NotBlank(message = "Charge Period cannot be empty")
    private String chargePeriod;
}

