package com.example.celog.member.service;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ResponseUtils;
import com.example.celog.common.SuccessLoginResponse;
import com.example.celog.common.SuccessResponse;
import com.example.celog.exception.CustomException;
import com.example.celog.auth.jwt.JwtUtil;
import com.example.celog.auth.jwt.RefreshToken;
import com.example.celog.auth.jwt.RefreshTokenRepository;
import com.example.celog.auth.jwt.TokenDto;
import com.example.celog.member.dto.LoginRequestDto;
import com.example.celog.member.dto.SignupRequestDto;
import com.example.celog.member.entity.Member;
import com.example.celog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.example.celog.exception.enumclass.Error.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    /**
     * 회원가입 기능
    * */
    @Transactional
    public ApiResponseDto<SuccessResponse> signup(SignupRequestDto signupRequestDto) {
        memberCheck(signupRequestDto.getEmail());
        memberRepository.save(Member.of(signupRequestDto));
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"회원가입 성공"));
    }

    /**
     * 로그인 기능
     */
    @Transactional
    public ApiResponseDto<SuccessLoginResponse> login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isEmpty() || !password.equals(findMember.get().getPassword())){
            throw new CustomException(NOT_EXIST_USER);
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


        return ResponseUtils.ok(SuccessLoginResponse.of(HttpStatus.OK,"로그인 성공", findMember.get().getNickname()));
    }


    @Transactional
    public ApiResponseDto<SuccessResponse> memberCheck(String email)  {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isPresent()){
            throw new CustomException(DUPLICATED_EMAIL);
        }
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "사용 가능한 계정입니다."));
    }

    /**
     * 토큰 갱신
     **/
    public ApiResponseDto<SuccessResponse> issueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");
        if(!jwtUtil.refreshTokenValidation(refreshToken)){
            throw new CustomException(WRONG_TOKEN);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(jwtUtil.getUserId(refreshToken), "Access"));
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "토큰 갱신 성공."));
    }

}