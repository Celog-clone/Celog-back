package com.example.celog.member.service;

import com.example.celog.common.SuccessResponse;
import com.example.celog.jwt.JwtUtil;
import com.example.celog.jwt.RefreshTokenRepository;
import com.example.celog.member.dto.SignupRequestDto;
import com.example.celog.member.entity.Member;
import com.example.celog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SuccessResponse signup(SignupRequestDto signupRequestDto) throws IllegalAccessException {
        // 회원가입 유저가 있는지 확인하는 부분
        // 기존에 id체크하는 부분이 있어서 이 부분 관련 협의 필요
        memberCheck(signupRequestDto.getEmail());
        memberRepository.save(
                Member.builder()
                        .nickname(signupRequestDto.getNickname())
                        .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                        .email(signupRequestDto.getEmail())
                        .build());
        return SuccessResponse.of(HttpStatus.OK,"회원가입 성공");
    }
    @Transactional
    public void memberCheck(String email) throws IllegalAccessException {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isPresent()){
            throw new IllegalAccessException("중복회원 입니다.");
        }
    }

}