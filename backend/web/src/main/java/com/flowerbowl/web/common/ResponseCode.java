package com.flowerbowl.web.common;

public interface ResponseCode {

    String SUCCESS = "SU"; // 성공

    String BAD_REQUEST = "FA"; // 400 에러
    String DUPLICATE_ID = "DI"; // 회원 가입 시 중복 아이디 에러
    String DUPLICATE_NICKNAME = "DN"; // 회원 가입 시 중복 닉네임 에러
    String DUPLICATE_EMAIL = "DE"; // 회원 가입 시 중복 이메일
    String CERTIFICATION_FAIL = "CF"; // 회원 가입 시 이메일 인증 실패
    String SIGN_IN_FAIL = "SF"; // 로그인 실패


    String MAIL_FAIL = "MF"; // 메일 발송 실패
    String INTERNAL_SERVER_ERROR = "ISE"; // 500 에러


}
