package me.forklift.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotNull(message = "email cannot be null")
    @Size(min = 2, message = "email not be less than 2 characters")
    @Email(message = "not email format")
    private String email;

    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "name not be less than 2 characters")
    private String name;

    @NotNull(message = "password cannot be null")
    @Size(min = 8, message = "password not be less than 2 characters")
    private String password;
}
