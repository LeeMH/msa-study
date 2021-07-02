package me.forklift.userservice.dto;

import lombok.Data;
import me.forklift.userservice.vo.ResponseOrder;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String name;
    private String password;
    private String userId;
    private Date createdAt;
    private String encryptedPassword;

    private List<ResponseOrder> orders;
}
