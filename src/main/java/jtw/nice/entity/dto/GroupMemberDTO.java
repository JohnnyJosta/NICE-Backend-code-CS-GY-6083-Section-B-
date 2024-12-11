package jtw.nice.entity.dto;

import lombok.Data;

@Data
public class GroupMemberDTO {
    private Integer id;
    private String name;
    private String birthDate;
    private String phone;
    private String email;
    private String gender;
    private String address;
}
