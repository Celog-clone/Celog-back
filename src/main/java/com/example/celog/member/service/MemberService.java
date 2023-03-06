package com.example.celog.member.service;

import com.example.celog.common.SuccessResponse;
import com.example.celog.jwt.JwtUtil;
import com.example.celog.jwt.RefreshToken;
import com.example.celog.jwt.RefreshTokenRepository;
import com.example.celog.jwt.TokenDto;
import com.example.celog.member.dto.LoginRequestDto;
import com.example.celog.member.dto.SignupRequestDto;
import com.example.celog.member.entity.Member;
import com.example.celog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    /**
     * 회원가입 기능
    * */
    @Transactional
    public SuccessResponse signup(SignupRequestDto signupRequestDto) {

        memberRepository.save(
                Member.builder()
                        .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                        .email(signupRequestDto.getEmail())
                        .nickname(signupRequestDto.getNickname())
                        .build());

        return SuccessResponse.of(HttpStatus.OK,"회원가입 성공");
    }

    /**
     * 로그인 기능
     */
    @Transactional
    public SuccessResponse login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isEmpty() || !passwordEncoder.matches(password,findMember.get().getPassword())){
            throw new NullPointerException("회원이 없습니다.");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(email);

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findAllByMemberId(email);

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefresh_Token()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefresh_Token(), email);
            refreshTokenRepository.save(newToken);
        }

        jwtUtil.setHeader(response, tokenDto);


        return SuccessResponse.of(HttpStatus.OK,"로그인 성공");
    }


    @Transactional
    public void memberCheck(String email) throws IllegalAccessException {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isPresent()){
            throw new IllegalAccessException("중복회원 입니다.");
        }
    }

    /**
     * 토큰 갱신
     **/
    public SuccessResponse issueToken(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException {
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");
        if(!jwtUtil.refreshTokenValidation(refreshToken)){
            throw new IllegalAccessException("토큰이 유효하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(jwtUtil.getUserId(refreshToken), "Access"));
        return SuccessResponse.of(HttpStatus.OK, "토큰 갱신 성공.");
    }

}