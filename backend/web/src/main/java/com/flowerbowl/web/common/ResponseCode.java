package com.flowerbowl.web.common;

public interface ResponseCode {

    String SUCCESS = "SU"; // 성공

    String BAD_REQUEST = "FA"; // 400 에러
    String DUPLICATE_ID = "DI"; // 회원 가입 시 중복 아이디 에러
    String DUPLICATE_NICKNAME = "DN"; // 회원 가입 시 중복 닉네임 에러
    String DUPLICATE_EMAIL = "DE"; // 회원 가입 시 중복 이메일
    String CERTIFICATION_FAIL = "CF"; // 회원 가입 시 이메일 인증 실패
    String SIGN_IN_FAIL = "SF"; // 로그인 실패
    String INVALID_PASSWORD = "IP"; // 비밀 번호 유호성 검사 실패
    String NOT_EXIST_LESSON = "NL"; // 조회한 레슨에 갯수가 0개인 경우
    String NOT_EXIST_RECIPE = "NR"; // 조회한 레시피 갯수가 0개인 경우
    String NOT_EXIST_REVIEW = "NV"; // 조회한 리뷰가 갯수가 0개인 경우
    String NOT_EXIST_USER = "NU"; // 존재하지 않은 유저 정보
    String NOT_EXIST_NUM = "NN"; // @pathvariable 잘못 입력한 경우
    String FIND_ID_FAIL = "EI"; // 아이디 찾기 실패
    String FIND_PW_FAIL = "EP"; // 비번 찾기 시 실패



    String MAIL_FAIL = "MF"; // 메일 발송 실패
    String INTERNAL_SERVER_ERROR = "ISE"; // 500 에러


}
