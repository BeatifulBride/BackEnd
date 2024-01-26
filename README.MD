# Beautiful Bride :: Backend :: SpringBoot

## 프로젝트 개요
#### Beautiful Bride는 웨딩 드레스 쇼핑을 새롭게 바꾸는 프로젝트입니다. 
이 프로젝트의 핵심은 사용자들에게 가상으로 웨딩 드레스를 입어볼 수 있는 체험을 제공하는 것입니다. 
이를 위해 사용자의 사진과 선택한 드레스를 기반으로 3D 모델을 생성하며, 이 과정은 백엔드 서버에서 처리됩니다.

백엔드 서버는 다음과 같은 기능을 담당합니다:
1. **드레스 데이터 관리**: 등록된 드레스의 데이터를 저장하고 관리합니다. 사용자는 이 중에서 원하는 드레스를 선택할 수 있습니다.
2. **사용자 데이터 관리**: 사용자의 개인 정보, 선택한 드레스, 사진 등을 관리합니다.
3. **시착 서비스**: 사용자의 사진과 선택한 드레스를 기반으로 이미지를 생성합니다. 이 모델은 사용자가 드레스를 가상으로 입어볼 수 있게 합니다.
4. **사용자 인증**: 회원 가입, 로그인, 비밀번호 재설정 등 사용자 인증 관련 기능을 처리합니다.

## 개발 환경 및 사용 기술
- JAVA 17
- Spring Boot 3.2.0
- Spring Security, Web, Validation, Services, Data JPA, ORM
- Querydsl 5.0.0
- MySQL JDBC
- Spring Doc OpenAPI (Swagger)
- Java JWT

## API 명세서
####
<details>
<summary>글자 명세서</summary>

- **URL** : /com/mypage/modify
- **Method** : PUT
- **Description** : 업체 마이 페이지 정보 수정
####
- **URL** : /com/mypage
- **Method** : POST
- **Description** : 업체 마이 페이지 정보 조회  
####
- **URL** : /tryon/starttryon
- **Method** : POST
- **Description** : 사용자가 선택한 드레스의 인덱스값과 사용자의 사진 전송
####
- **URL** : /find/account/pwd
- **Method** : POST
- **Description** : 비밀번호 재설정 요청
####
- **URL** : /find/account/id
- **Method** : POST
- **Description** : 아이디 찾기 요청
####
- **URL** : /auth/signup/member
- **Method** : POST
- **Description** : '멤버'회원 가입 요청
####
- **URL** : /auth/signup/company
- **Method** : POST
- **Description** : '업체'회원 가입 요청
####
- **URL** : /auth/login
- **Method** : POST
- **Description** : 로그인 요청
####
- **URL** : /find/account/check/{loginId}/{loginEmail}
- **Method** : GET
- **Description** : 사용자가 계정을 찾기 위한 요청
####
- **URL** : /auth/idcheck/{loginId}
- **Method** : GET
- **Description** : 회원가입-기존 아이디 여부 확인
####
- **URL** : /auth/emailcheck/{loginEmail}
- **Method** : GET
- **Description** : 회원가입-기존 이메일 여부 확인
####
- **URL** : /dress/newdress
- **Method** : POST
- **Description** : 새 드레스를 등록
####
- **URL** : /dress/top5
- **Method** : GET
- **Description** : 전체 드레스에서 등록된 드레스 목록 상위 5개를 반환
####
- **URL** : /dress/list
- **Method** : GET
- **Description** : 등록된 드레스 목록을 반환
####
- **URL** : /dress/del/{dressIndex}
- **Method** : DELETE
- **Description** : 드레스 삭제 요청. 해당되는 업체 회원이 아닐 경우에 반려됨
####
- **URL** : /mem/mypage/mymark/list
- **Method** : GET
- **Description** : 마이페이지 - 일반 유저가 즐겨찾기한 드레스의 리스트를 반환
####
- **URL** : /mem/mark/{dressIndex}
- **Method** : GET
- **Description** : 드레스 즐겨찾기를 추가
####
- **URL** : /mem/mark/mymark
- **Method** : GET
- **Description** : 일반 유저가 즐겨찾기한 드레스의 인덱스 리스트를 반환
####
- **URL** : /mem/maininfo
- **Method** : GET
- **Description** : 멤버 이름과 웨딩 날짜를 반환
</details>

####
<img src="readme img/apis.png">
