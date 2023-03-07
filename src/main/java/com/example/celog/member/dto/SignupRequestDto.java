package com.example.celog.member.dto;

import lombok.Getter;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @Pattern(regexp = "/^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;", message = "닉네임에러")
    private String nickname;
    @Pattern(regexp = "/^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;", message = "패스워드에러")
    private String password;
    @Pattern(regexp = "/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;", message = "이메일에러")
    private String email;
}
