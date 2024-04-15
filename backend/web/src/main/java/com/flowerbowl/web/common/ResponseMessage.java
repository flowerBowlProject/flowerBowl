package com.flowerbowl.web.common;

public interface ResponseMessage {

    /**
     *로그인 응답 코드 및 메시지
     */
    String SUCCESS = "success"; // 성공
    String CREATED = "Created"; // 생성 완료

    String BAD_REQUEST = "Bad Request"; // 400 에러
    String DUPLICATE_ID = "Duplicate Id"; // 회원 가입 시 중복 아이디 에러
    String DUPLICATE_NICKNAME = "Duplicate Nickname"; // 회원 가입 시 중복 닉네임 에러
    String DUPLICATE_EMAIL = "Duplicate Email"; // 회원 가입 시 중복 이메일
    String CERTIFICATION_FAIL = "Certification fail"; // 회원 가입 시 이메일 인증 실패
    String SIGN_IN_FAIL = "Login information mismatch"; // 로그인 실패
    String INVALID_PASSWORD = "Invalid Password"; // 비밀 번호 유호성 검사 실패
    String NOT_EXIST_USER = "This user does not exist"; // 로그인 실패
    String NOT_EXIST_LESSON = "This lesson does not exist"; // 조회한 레슨에 갯수가 0개인 경우
    String NOT_EXIST_RECIPE = "This recipe does not exist"; // 조회한 레시피 갯수가 0개인 경우
    String NOT_EXIST_REVIEW = "This review does not exist"; // 조회한 리뷰가 갯수가 0개인 경우
    String NOT_EXIST_NUM = "This num does not exist"; // @pathvariable 잘못 입력한 경우
    String NOT_EXIST_PAGE = "This page does not exist"; // 존재하지 않는 페이지인 경우
    String FIND_ID_FAIL = "Email incorrect"; // 아이디 찾기 실패
    String FIND_PW_FAIL = "Information mismatch"; // 비번 찾기 실패


    String MAIL_FAIL = "Mail Send Fail"; // 메일 발송 실패
    String INTERNAL_SERVER_ERROR = "Internal Server Error"; // 500 에러
}
