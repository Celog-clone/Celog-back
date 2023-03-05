package com.example.celog.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {

    // 400 BAD_REQUEST 잘못된 요청
    NOT_FOUND_USER("400", "아이디 비밀번호 오류입니다."),

    NOT_FOUND_POST("400", "게시물을 찾을 수 없습니다."),

    NOT_FOUND_MEMBER("400", "로그인이 필요합니다."),

    NOT_FOUND_SEARCH("400", "검색 결과가 없습니다."),

    NO_AUTHORITY_MODIFY("400",  "수정 권한이 없습니다."),

    NO_AUTHORITY_DELETE("400",  "삭제 권한이 없습니다."),
    WRONG_TOKEN("400", "토큰 오류"),
    WRONG_PASSWORD_CHECK( "400", "비밀번호, 비밀번호 확인 값 서로 불일치"),
    VALIDATE_EMAIL_ERROR( "400", "이메일 형식이 아닙니다."),

    NOT_FOUND_DATE("400", "일정을 찾을 수 없습니다."),

    VALIDATE_NICKNAME_ERROR( "400", "닉네임은 알파벳 대, 소문자, 숫자로 구성된 2-12자리여야 한다."),

    NOT_EXIST_USER( "400", "회원을 찾을 수 없습니다."),

    // 401 UNAUTHORIZED 비인증
    INVALID_AUTHORIZED( "401", "사용자 미인증"),

    WRONG_LIKE_REQUEST("400", "잘못된 값을 요청하였습니다."),




    // 409 CONFLICT 중복된 리소스
    DUPLICATED_EMAIL("409", "이미 존재하는 이메일입니다."),
    DUPLICATED_NICKNAME("409", "이미 존재하는 닉네임입니다.");

    private final String status;
    private final String message;
}