package com.example.celog.member.controller;

import com.example.celog.common.SuccessResponse;
import com.example.celog.member.dto.SignupRequestDto;
import com.example.celog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 기능 Controller
     */

    @PostMapping("/members/signup")
    public SuccessResponse signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult result) throws IllegalAccessException {
        if (result.hasErrors()){
            if (result.getFieldError().getDefaultMessage().equals("패스워드에러"))
                throw new IllegalAccessException("패스워드 형식이 아닙니다.");
            throw new IllegalAccessException("이메일 형식이 아닙니다.");
        }
        return memberService.signup(signupRequestDto);
    }


}