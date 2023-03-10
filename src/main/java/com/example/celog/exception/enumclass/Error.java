package com.example.celog.exception.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {

    // 400 BAD_REQUEST 잘못된 요청
    NOT_FOUND_ID("400", "아이디 오류입니다."),
    PASSWORD_WRONG("400","잘못된 패스워드 입니다"),
    NOT_FOUND_POST("400", "게시물을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER("400", "로그인이 필요합니다."),
    NOT_FOUND_SEARCH("400", "검색 결과가 없습니다."),
    NO_AUTHORITY("400", "권한이 없습니다."),
    WRONG_TOKEN("400", "토큰 오류"),
    WRONG_PASSWORD_CHECK( "400", "비밀번호 형식이 아닙니다"),
    VALIDATE_EMAIL_ERROR( "400", "이메일 형식이 아닙니다."),
    VALIDATE_NICKNAME_ERROR( "400", "닉네임은 알파벳 대, 소문자, 숫자로 구성된 2-12자리여야 한다."),
    NOT_MY_CONTENT("400", "작성자만 수정/삭제가 가능합니다"),
    NOT_EXIST_COMMENT("400","댓글이 존재하지 않습니다"),
    NOT_EXIST_USER("400","사용자가 없습니다"),
    BAD_REQUEST("400", ""),

    // S3
    FAIL_S3_SAVE("400", "S3파일 저장 중 예외 발생"),
    DELETE_S3_FILE("400","s3에 저장되었던 파일 삭제"),

    // 409 CONFLICT 중복된 리소스
    DUPLICATED_EMAIL("409", "이미 존재하는 이메일입니다."),
    DUPLICATED_NICKNAME("409", "이미 존재하는 닉네임입니다.");

    private final String status;
    private final String message;
}