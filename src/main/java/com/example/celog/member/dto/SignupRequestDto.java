package com.example.celog.member.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    private String password;

    private String email;
}
