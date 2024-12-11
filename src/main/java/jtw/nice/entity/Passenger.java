package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@TableName("jtw_passenger")
public class Passenger {

    @TableId(value = "passenger_id", type = IdType.AUTO)
    private Integer passengerId;

    @TableField("name")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @TableField("date_of_birth")
    @NotNull(message = "Date of Birth cannot be empty")
    private Date dateOfBirth;

    @TableField("gender")
    @NotBlank(message = "Gender cannot be empty")
    private String gender;

    @TableField("phone_number")
    @NotBlank(message = "Phone Number cannot be empty")
    private String phoneNumber;

    @TableField("nationality")
    @NotBlank(message = "Nationality cannot be empty")
    private String nationality;

    @TableField("email")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email format is invalid")
    private String email;

    @TableField("addr_nation")
    @NotBlank(message = "Address Nation cannot be empty")
    private String addrNation;

    @TableField("addr_state")
    @NotBlank(message = "Address State cannot be empty")
    private String addrState;

    @TableField("addr_city")
    @NotBlank(message = "Address City cannot be empty")
    private String addrCity;

    @TableField("addr_street")
    @NotBlank(message = "Address Street cannot be empty")
    private String addrStreet;

    @TableField("addr_zipcode")
    @NotBlank(message = "Address Zipcode cannot be empty")
    private String addrZipcode;

    @TableField("group_id")
    private Integer groupId;
}
