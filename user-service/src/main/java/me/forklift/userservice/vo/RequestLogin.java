package me.forklift.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "email은 필수 입니다.")
    @Size(min = 5, message = "email은 최소 2글자 이상입니다.")
    @Email(message = "이메일 타입이 아닙니다.")
    private String email;

    @NotNull(message = "패스워드는 필수 입니다.")
    @Size(min = 8, message = "패스워드는 최소 8자 이상입니다.")
    private String password;
}
