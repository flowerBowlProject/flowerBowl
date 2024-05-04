package com.flowerbowl.web.common;

public interface ResponseCode {

    String SUCCESS = "SU"; // 성공

    String CREATED = "CR"; // 생성 완료
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
    String NOT_EXIST_PAGE = "NPG"; // 존재하지 않는 페이지인 경우
    String NOT_EXIST_COMMUNITY = "NC"; // 존재하지 않는 커뮤니티 게시글인 경우
    String NOT_EXIST_CATEGORY = "NCT"; // 존재하지 않는 카테고리인 경우
    String NOT_EXIST_COMMENT = "NCM"; // 존재하지 않는 댓글인 경우
    String FIND_ID_FAIL = "EI"; // 아이디 찾기 실패
    String FIND_PW_FAIL = "EP"; // 비번 찾기 시 실패
    String WRONG_BOARD_TYPE = "WT"; // 잘못된 게시판 타입인 경우
    String NOT_MATCH_USER = "MU"; // 동일 유저 체크
    String WITHDRAWAL_USER = "WU"; // 회원 탈퇴한 유저가 로그인 시
    String DUPLICATE_LICENSE = "DL"; // 중복 쉐프 신청 시


    String WRONG_FILE_EXTENSION = "WF"; // 허용되지 않은 파일 확장자인 경우
    String UPLOAD_FAIL = "UF"; // 파일 업로드 실패
    String CONVERT_FAIL = "CF"; // 파일 변환 실패
    String MAIL_FAIL = "MF"; // 메일 발송 실패
    String INTERNAL_SERVER_ERROR = "ISE"; // 500 에러
    String DOES_NOT_MATCH = "DM"; // 두 비교군이 일치하지 않는 경우


}
