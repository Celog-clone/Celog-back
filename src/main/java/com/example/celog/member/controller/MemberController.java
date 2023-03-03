package com.example.celog.member.controller;

import com.example.celog.common.SuccessResponse;
import com.example.celog.member.dto.LoginRequestDto;
import com.example.celog.member.dto.SignupRequestDto;
import com.example.celog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 로그인 메서드
     **/
    @PostMapping("/members/login")
    public SuccessResponse login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        return memberService.login(requestDto,response);
    }

    /**
     * 회원명 중복 체크
     */

    @GetMapping("/members")
    public SuccessResponse memberCheck( @RequestParam("email") String email) throws IllegalAccessException {
        memberService.memberCheck(email);
        return SuccessResponse.of(HttpStatus.OK,"사용가능한 계정입니다");
    }


}