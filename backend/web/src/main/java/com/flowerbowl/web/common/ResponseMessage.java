package com.flowerbowl.web.common;

public interface ResponseMessage {

    /**
     *로그인 응답 코드 및 메시지
     */
    String SUCCESS = "success"; // 성공

    String BAD_REQUEST = "Bad Request"; // 400 에러
    String DUPLICATE_ID = "Duplicate Id"; // 회원 가입 시 중복 아이디 에러
    String DUPLICATE_NICKNAME = "Duplicate Nickname"; // 회원 가입 시 중복 닉네임 에러
    String DUPLICATE_EMAIL = "Duplicate Email"; // 회원 가입 시 중복 이메일
    String CERTIFICATION_FAIL = "Certification fail"; // 회원 가입 시 이메일 인증 실패
    String SIGN_IN_FAIL = "Login information mismatch"; // 로그인 실패

    String INVALID_PASSWORD = "Invalid Password"; // 비밀 번호 유호성 검사 실패
    String NOT_EXIST_USER = "This user does not exist"; // 로그인 실패


    String MAIL_FAIL = "Mail Send Fail"; // 메일 발송 실패
    String INTERNAL_SERVER_ERROR = "Internal Server Error"; // 500 에러
}
