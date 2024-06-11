# 화반

## 목차
  * [프로젝트 소개](#-매번-끼니를-어떻게-해결하고-계신가요)
  * [👨‍👩‍👧‍👦 멤버 및 역할 소개](#멤버-및-역할-소개)
  * [💻 기술 스텍](#-기술-스텍)
      - [Frontend](#frontend)
      - [Backend](#backend)
  * [📃 사용자 요구사항 정의서 / 화면 정의서 / 테이블 명세서 / API 명세서](#-사용자-요구사항-정의서--화면-정의서--테이블-명세서--api-명세서)
- [구현영상 - Web](#구현영상---web)
  * [[메인페이지 기능]](#메인페이지-기능)
    + [카테고리별 조회](#카테고리별-조회)
    + [인기 레시피 상세페이지 조회](#인기-레시피-상세페이지-조회)
    + [인기 클래스 상세페이지 조회](#인기-클래스-상세페이지-조회)
    + [header / footer 조회](#header--footer-조회)
    + [배너 등록 및 조회](#배너-등록-및-조회)
  * [[회원 관련 기능]](#회원-관련-기능)
    + [회원가입 및 로그인](#회원가입-및-로그인)
    + [아이디 및 비밀번호 찾기](#아이디-및-비밀번호-찾기)
    + [마이페이지](#마이페이지)
    + [회원 탈퇴](#회원-탈퇴)
  * [[클래스 기능]](#클래스-기능)
    + [클래스 등록](#클래스-등록)
    + [클래스 조회](#클래스-조회)
    + [클래스 수정](#클래스-수정)
    + [클래스 삭제](#클래스-삭제)
    + [클래스 북마크 등록 및 해제](#클래스-북마크-등록-및-해제)
    + [클래스 구매](#클래스-구매)
  * [[레시피 관련 기능]](#레시피-관련-기능)
    + [레시피 등록](#레시피-등록)
    + [레시피 수정](#레시피-수정)
    + [레시피 조회](#레시피-조회)
    + [레시피 삭제](#레시피-삭제)
    + [레시피 북마크 등록 및 해제](#레시피-북마크-등록-및-해제)
  * [[커뮤니티 관련 기능]](#커뮤니티-관련-기능)
    + [커뮤니티 등록](#커뮤니티-등록)
    + [커뮤니티 수정](#커뮤니티-수정)
    + [커뮤니티 조회](#커뮤니티-조회)
    + [커뮤니티 삭제](#커뮤니티-삭제)
  * [[댓글 기능]](#댓글-기능)
    + [댓글 등록 / 수정 / 삭제](#댓글-등록--수정--삭제)
  * [[검색 기능]](#검색-기능)
    + [제목에 있는 키워드 검색](#제목에-있는-키워드-검색)
    + [내용에 있는 키워드 검색](#내용에-있는-키워드-검색)
    + [재료에 있는 키워드 검색](#재료에-있는-키워드-검색)
    + [제목/내용/재료에 있는 키워드 총 검색](#제목내용재료에-있는-키워드-총-검색)

</br><br>
## 프로젝트 소개
### 🍚 매번 끼니를 어떻게 해결하고 계신가요?

최근 몇년간 배달음식 소비가 증가하고 있습니다. 그에 따라 매일 요리하는 사람들의 비율은 감소하고 있습니다.<br/>
이러한 배경 아래, 저희는 바쁜 사람들에게 더 건강한 한 끼와 요리의 즐거움을 느껴보기를 바라는 마음으로 화반을 개발하게 되었습니다.

_매일 3번의 끼니, 저희 **화반**과 함께 건강하고 즐거운 식사하세요._
</br>
</br></br>

## 👨‍👩‍👧‍👦 멤버 및 역할 소개

| 이름                                          | 역할                                                                                                                  |
| --------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| ~~박용창:타올~~                               | <span style="color:red">**중도하차멤버**</span> 피그마 디자인, 메인페이지, 회원가입                                   |
| [김희진:청바지](https://github.com/Khjin06k)  | 피그마 디자인, 레시피/클래스/커뮤니티 관련 페이지 제작 및 기능 구현, 댓글 컴포넌트 구현, 북마크 및 카드 컴포넌트 구현 |
| [정진희:조이](https://github.com/JoyWorlds)   | 피그마 디자인, 마이페이지/관리자 페이지 제작 및 기능 구현, 결제내역, 리뷰작성                                         |
| [김필준: 네오](https://github.com/peeljunKim) | 회원가입 및 로그인(JWT), 마이페이지, oauth2.0 로그인, DB설계, HTTPS 웹 서버 구축, ci/cd                               |
| [김창훈: 라이언](https://github.com/C-H-Kim)  | 레시피 기능, 커뮤니티 기능, 댓글 기능, 이미지 업로드 기능, ci/cd                                                      |
| [최현용: 무지](https://github.com/H2ll0World) | 관리자 기능, 클래스 기능, 결제연동, 검색 기능, ci/cd                                                                  |

</br>
</br>

## 💻 기술 스텍

#### Frontend

- 언어

  <img src="https://img.shields.io/badge/html-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"> <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white">

- 라이브러리

  <img src="https://img.shields.io/badge/mui-007FFF?style=for-the-badge&logo=mui&logoColor=white"> <img src="https://img.shields.io/badge/redux-764ABC?style=for-the-badge&logo=redux&logoColor=white">

- 서버

    <img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white">

- CI/CD

    <img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">

<br/>

#### Backend

- 언어

  <img src="https://img.shields.io/badge/Java17-007396?style=for-the-badge&logo=Java&logoColor=white">

- 프레임워크

  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">

- 서버

  <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon AWS S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"> <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">

- ORM

  <img src="https://img.shields.io/badge/spring data-6DB33F?style=for-the-badge&logo=spring&logoColor=white">

- DB

  <img src="https://img.shields.io/badge/My sql-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">

- CI/CD

  <img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
</br><br>

## 📃 사용자 요구사항 정의서 / 화면 정의서 / 테이블 명세서 / API 명세서
- 링크 : https://docs.google.com/spreadsheets/d/12LKp0_4OMoveO8XGUlzghtdq8SMO4uuRq8Ro9oIRZy8/edit?usp=sharing
</br></br><br>

# 구현영상 - Web

## [메인페이지 기능]

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/cae3e6d0-a186-447d-94ff-b40de53684e5

### 카테고리별 조회
- 각 카테고리에 해당되는 레시피와 클래스 조회가 가능합니다. <br></aside>
<br>

### 인기 레시피 상세페이지 조회
- 레시피 인기 상위 5개가 노출되며, 각 상세 페이지 조회가 가능합니다. <br></aside>
<br>

### 인기 클래스 상세페이지 조회
- 클래스 인기 상위 3개가 노출되며, 각 상세 페이지 조회가 가능합니다. <br></aside>
<br>

### header / footer 조회
- 헤더 및 푸터에 있는 모든 버튼 클릭 시 각 기능에 맞는 페이지로 이동합니다. <br></aside>
<br>


### 배너 등록 및 조회

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/b68a03a2-c94d-4ce0-bc4b-2f3f982c7a6d

- 배너 등록은 관리자만 가능합니다. <br>
- 등록된 배너는 메인페이지에 노출됩니다.</aside>
<br>


## [회원 관련 기능]
### 회원가입 및 로그인

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/cb5d253f-d1e9-4866-8cc4-816f51691894

- 홈페이지 자체 회원가입 및 로그인 / 소셜 회원가입 및 로그인을 구현했습니다. <br>
- 소셜 로그인 시 닉네임이 중복되는 경우 랜덤으로 닉네임이 부여되며 변경가능하도록 마이페이지로 이동합니다. </aside>
<br>


### 아이디 및 비밀번호 찾기

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/c98a4804-0b3e-4a34-9ec4-38d3beb7f913

- 아이디 및 비밀번호 찾기 기능을 구현했습니다. <br>
- 비밀번호 찾기는 랜덤 비밀번호가 이메일로 발송되며, 해당 비밀번호로 로그인 가능합니다. </aside>
<br>


### 마이페이지

#### 유저

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/c83e8758-7cbe-4169-bf95-c2720c2e7f3f

#### 쉐프

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/eb2b5461-682c-4f44-9675-142e7a9a6e76

- 로그인 시 마이페이지의 항목은 위와 같습니다. <br>
- 유저는 쉐프 신청 기능이 존재하며, 이미 쉐프 신청이 완료되었다면 쉐프 표시만 나타납니다. <br>
- 쉐프로 역할 변경 시 쉐프만 사용 가능한 기능(창작 클래스 조회 및 클래스 신청자 내역)이 추가되어 있습니다. </aside>
<br>


### 회원 탈퇴

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/b15569e9-8a64-41ed-b7fb-a32dc2388f43

- 회원 탈퇴가 가능합니다. <br>
- 회원 탈퇴 시 닉네임은 (Unknown)으로 변경됩니다. </aside>
<br>


## [클래스 기능]

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/9f3da4dc-e3a1-4b8c-b81d-01b8a9ca851f

### 클래스 등록
- 클래스 등록은 쉐프만 가능합니다. <br>
- 모든 정보를 입력해야 등록이 가능합니다 </aside>
<br>

### 클래스 조회
- 클래스 조회가 가능합니다.<br></aside>
<br>

### 클래스 수정
- 클래스 수정이 가능합니다. <br>
- 글 작성자에게만 수정 버튼이 노출됩니다. </aside>
<br>

### 클래스 삭제
-  클래스 삭제가 가능합니다.<br>
-  삭제 시 되돌릴 수 없으며, 글 작성자에게만 삭제 버튼이 노출됩니다.</aside>
<br>

### 클래스 북마크 등록 및 해제
- 북마크 등록 및 해제가 가능합니다. <br>
- 등록한 북마크는 마이페이지에서 확인 가능합니다. </aside>
<br>

### 클래스 구매
- 클래스 구매가 가능합니다. <br>
- 구매한 클래스는 삭제가 되더라도 마이페이지에서 확인 가능합니다. </aside>
<br>


## [레시피 관련 기능]

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/b87b25e7-8410-465e-8928-b5904a013b7f

### 레시피 등록
- 모든 회원은 레시피를 등록 가능합니다. <br>
- 모든 정보를 입력해야 등록 가능합니다. </aside>
<br>

### 레시피 수정
- 레시피 수정이 가능합니다. <br>
- 글 작성자에게만 수정 버튼이 노출됩니다. </aside>
<br>

### 레시피 조회
- 작성된 레시피 리스트와 상세 레시피를 볼 수 있습니다. <br></aside>
<br>

### 레시피 삭제
-  레시피 삭제가 가능합니다.<br>
-  삭제 시 되돌릴 수 없으며, 글 작성자에게만 삭제 버튼이 노출됩니다.</aside>
<br>

### 레시피 북마크 등록 및 해제
-  북마크 등록 및 해제가 가능합니다. <br>
- 등록한 북마크는 마이페이지에서 확인 가능합니다. </aside>
<br>


## [커뮤니티 관련 기능]

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/46b5fd25-2869-4c5f-85b4-21563ecd695d

### 커뮤니티 등록
- 모든 회원은 커뮤니티 글 등록이 가능합니다. <br>
- 모든 정보를 입력해야 등록 가능합니다. </aside>
<br>

### 커뮤니티 수정
- 커뮤니티 수정이 가능합니다. <br>
- 글 작성자에게만 수정 버튼이 노출됩니다. </aside>
<br>

### 커뮤니티 조회
- 작성된 커뮤니티 리스트와 상세 내용을 볼 수 있습니다. <br></aside>
<br>

### 커뮤니티 삭제
-  커뮤니티 삭제가 가능합니다.<br>
-  삭제 시 되돌릴 수 없으며, 글 작성자에게만 삭제 버튼이 노출됩니다.</aside>
<br>


## [댓글 기능]
### 댓글 등록 / 수정 / 삭제

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/afd85a58-52b9-4547-86f7-73e7c87370d1

- 레시피와 커뮤니티는 댓글 기능이 존재합니다. <br>
- 댓글 및 대댓글 작성, 수정 및 삭제가 가능합니다. </aside>
<br>


## [검색 기능]

https://github.com/flowerBowlProject/flowerBowl/assets/95160590/5ba61342-eddd-4e3e-b0e1-babb71fe978e

### 제목에 있는 키워드 검색
- 키워드 검색 시 제목에 키워드가 포함되어 있는 글을 조회 가능합니다. <br></aside>
<br>

### 내용에 있는 키워드 검색
- 키워드 검색 시 내용에 키워드가 포함되어 있는 글을 조회 가능합니다. <br></aside>
<br>

### 재료에 있는 키워드 검색
- 키워드 검색 시 재료에 키워드가 포함되어 있는 글을 조회 가능합니다. <br></aside>
<br>

### 제목/내용/재료에 있는 키워드 총 검색
- 키워드 검색 시  제목/내용/재료에 키워드가 포함되어 있는 글을 조회 가능합니다. <br></aside>
<br>
