# ☯ 흑백엔드

# 📆 프로젝트 진행 기간

***

2024.10.28(월) ~ 2024.11.14(목) (18일간 진행)  
Oreumi 백엔드 6기 2차 프로젝트

***

# 🎈프로젝트 소개

***

임영웅을 사랑하는 팬들이 모여 자유롭게 소통하고 다양한 정보를 공유할 수 있는 커뮤니티 웹사이트입니다.
이 사이트는 팬들 간의 교류를 활성화하고, 팬들이 서로 소통하며 정보를 나눌 수 있는 공간을 제공합니다.
팬들은 자유게시판, 등업게시판, 정모게시판에서 다양한 주제로 이야기를 나누고,
신고 및 문의 기능을 통해 운영진과 소통할 수 있습니다.

***

# 💜 주요 기능

***

- **회원 관리**
    - 회원가입, 로그인, 로그아웃 기능을 제공합니다.
    - 프로필 업데이트 및 닉네임, 비밀번호 변경 등이 가능합니다.


- **게시판 기능**
    - 게시글 작성, 수정, 삭제 기능을 지원하며, 댓글 작성도 가능합니다.
    - 게시판 종류에 따라 자유 게시판, 등업 게시판, 정모 게시판으로 구분되어 있고 글 상세보기가 가능합니다.
    - 작성자 본인이 아닐 경우 수정, 삭제가 불가능 합니다.


- **검색 기능**
    - 키워드를 이용해 게시글을 검색할 수 있습니다.
    - 사용자 닉네임으로 게시글을 검색할 수 있는 기능이 포함되어 있습니다.


- **페이지네이션 지원**
    - 게시글 목록에 페이지네이션을 적용하여 한 페이지에 일정 개수의 게시글이 표시됩니다.
    - 페이지별로 게시글 목록을 쉽게 탐색할 수 있습니다.


- **댓글 기능**
    - 게시글에 댓글을 작성하고 수정, 삭제할 수 있습니다.
    - 답글 기능을 구현했습니다.
    - 작성자 본인이 아닐 경우 수정, 삭제가 불가능 합니다.


- **신고 / 문의 기능**
   - 신고 / 문의하기를 통해 관리자에게 게시글 신고 및 사이트에 대한 문의를 할 수 있습니다.
   - 작성자 본인과 관리자를 제외하고는 열람이 불가능 합니다 


- **관리자 기능**
    - 관리자는 사용자 및 게시글을 관리할 수 있으며, 부적절한 게시글을 숨김표시 할 수 있습니다.
    - 신고 / 문의 게시글 처리를 할 수 있습니다.

  
***

## 🎹 서비스 링크
### http://13.209.227.3:8080/

# 주요기술

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

***

# Project 구조

***

### ⬅ Back

```
📁 oreumifancafe
├── 📄 OreumiFancafeApplication
├── 📄 HomeController
├── 📁 aop
│   └── 📁 auth
│       ├── 📄 CustomAuthenticationEntryPoint
│       ├── 📄 CustomAuthenticationFailureHandler
│       ├── 📄 CustomAuthenticationSuccessHandler
│       ├── 📄 CustomAuthorizationManager
│       └── 📄 GlobalExceptionHandler
├── 📁 config
│   └── 📁 auth
│       └── 📄 SecurityConfig
├── 📁 constants
│   └── 📁 user
│       └── 📄 Regx
├── 📁 controller
│   ├── 📁 admin
│   │   └── 📄 AdminController
│   ├── 📁 auth
│   │   └── 📄 AuthController
│   ├── 📁 board
│   │   └── 📄 BoardController
│   ├── 📁 help
│   │   ├── 📄 HelpController
│   │   └── 📄 HelpPageController
│   ├── 📁 reply
│   │   └── 📄 ReplyController
│   └── 📁 user
│       └── 📄 UserController
├── 📁 domain
│   ├── 📁 board
│   │   └── 📄 Board
│   ├── 📁 boardcategory
│   │   └── 📄 BoardCategory
│   ├── 📁 boardtype
│   │   └── 📄 BoardType
│   └── 📁 dto
│       ├── 📁 admin
│       │   ├── 📄 BoardResponse
│       │   ├── 📄 UpdateBoardStateRequest
│       │   ├── 📄 UpdateRoleRequest
│       │   ├── 📄 UpdateStateRequest
│       │   └── 📄 UserInfoResponse
│       ├── 📁 board
│       │   └── 📄 AddBoardRequest
│       ├── 📁 help
│       │   ├── 📄 AddHelpRequest
│       │   └── 📄 HelpResponse
│       ├── 📁 reply
│       │   ├── 📄 AddReplyRequest
│       │   └── 📄 ReplyResponse
│       └── 📁 user
│           ├── 📄 AddUserRequest
│           └── 📄 UserResponse
├── 📁 help
│   └── 📄 Help
├── 📁 reply
│   └── 📄 Reply
├── 📁 user
│   ├── 📄 Role
│   └── 📄 User
├── 📁 exceptions
│   ├── 📄 ForbiddenException
│   ├── 📄 UnauthorizedException
│   └── 📄 UserNotFoundException
├── 📁 repository
│   ├── 📁 board
│   │   └── 📄 BoardRepository
│   ├── 📁 help
│   │   └── 📄 HelpRepository
│   ├── 📁 reply
│   │   └── 📄 ReplyRepository
│   ├── 📁 user
│   │   ├── 📄 RoleRepository
│   │   └── 📄 UserRepository
│   └── 📁 users
└── 📁 service
    ├── 📁 admin
    │   └── 📄 AdminService
    ├── 📁 auth
    │   └── 📄 CustomUserDetailsService
    ├── 📁 board
    │   └── 📄 BoardService
    ├── 📁 help
    │   └── 📄 HelpService
    ├── 📁 reply
    │   └── 📄 ReplyService
    └── 📁 user
        └── 📄 UserService
```

### ➡ Front

```
📁 resources
├── 📁 static
│   ├── 📁 css
│   │   ├── 📄 adminPage.css
│   │   ├── 📄 board.css
│   │   ├── 📄 editProfile.css
│   │   ├── 📄 footer.css
│   │   ├── 📄 header.css
│   │   ├── 📄 main.css
│   │   ├── 📄 myPageStyle.css
│   │   ├── 📄 nav.css
│   │   ├── 📄 post.css
│   │   ├── 📄 postEditor.css
│   │   ├── 📄 qnadetail.css
│   │   ├── 📄 qnaEditor.css
│   │   ├── 📄 registerPageStyle.css
│   │   └── 📄 section.css
│   ├── 📁 img
│   │   └── 📄 defaultProfileImage.jpeg
│   ├── 📁 js
│   │   ├── 📄 adminPage.js
│   │   ├── 📄 editProfile.js
│   │   ├── 📄 nav.js
│   │   ├── 📄 qnaEditor.js
│   │   └── 📄 registerPageStyle.js
│   └── 📄 reset.css
└── 📁 templates
    ├── 📁 footer
    │   └── 📄 footer.html
    ├── 📁 header
    │   └── 📄 header.html
    ├── 📁 nav
    │   └── 📄 nav.html
    ├── 📄 accessDenied.html
    ├── 📄 adminPage.html
    ├── 📄 authentication.html
    ├── 📄 board.html
    ├── 📄 board_search.html
    ├── 📄 editProfile.html
    ├── 📄 errorPage.html
    ├── 📄 index.html
    ├── 📄 myPage.html
    ├── 📄 post.html
    ├── 📄 postEditor.html
    ├── 📄 qnadetail.html
    ├── 📄 qnaEditor.html
    ├── 📄 registerPage.html
    └── 🖼️ 임시프로필사진.jpg
```

*** 

# 📋 ERD 구조

***

![ERD.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FERD.png)

***

# 📈 Figma 설계

***

![Figma.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FFigma.png)

***

# 🗓 개발 일정
***

![개발일정.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2F%EA%B0%9C%EB%B0%9C%EC%9D%BC%EC%A0%95.png)

***

# 🖨 API 명세

***

## 🧍🏻‍♂️ Users
| 📛NAME | 📎URL | ⚙︎METHOD | ❓︎DESCRIPTION | 비고 |
| --- | --- | --- | --- | --- |
| userInfo | /users/userinfo | GET | 마이페이지 사용자 정보 조회 | 내글, 내 댓글, 내 문의내역, 내 신고내역 포함 페이징 추가 |
| logout | /users/logout | GET | 유저 로그아웃 |  |
| duplicateEmail | /users/check/email/{email} | GET | 이메일 중복확인 |  |
| duplicateNickname | /users/check/nickname/{nickname} | GET | 닉네임 중복확인 |  |
| duplicateUserId | /users/check/userid/{userId} | GET | 아이디 중복확인 |  |
| signUp | /users | POST | 회원가입 |  |
| login | /users/login | POST | 유저 로그인 | ***소셜 들어가면 수정될 수 있음*** |
| updateUser | /users | PUT | 회원정보수정 |  |
| profileImageUpdate | /users/profileImage | PUT | 프로필 이미지 수정 |  |
| deleteUser | /users/{userId} | DELETE | 회원탈퇴 |  |
| updateInfoPage | /users/updateInfo | GET | 회원정보 수정 페이지 이동 |  |
| updateInfo | /users/updateInfo | PUT | 회원정보 수정 |  |

***

## 📋 BOARD
| 📛NAME | 📎URL | ⚙︎METHOD | ❓︎DESCRIPTION | 비고 |
| --- | --- | --- | --- | --- |
| boardPageCount | /board/{boardType}/pagecount | GET | 게시글 페이징에 필요한 게시글 페이지 카운트 |  |
| getAllBoard | /board/{boardType}/{pageNum} | GET | 게시물 타입에 따른 전체 게시물 조회 (페이징), 댓글 갯수도 같이  |  |
| findTitle | /board/title/{title}/{pageNum} | GET | 게시물 제목으로 검색 |  |
| findNickname | /board/nickname/{nickname}/{pageNum} | GET | 게시물 닉네임으로 검색 |  |
| findKeyword | /board/keyword/{keyword}/{pageNum} | GET | 게시글 제목 + 내용으로 검색 |  |
| showBoard | /board/new-article | GET | 게시글 생성 페이지 반환 |  |
| writeBoard | /board | POST | 게시물 타입에 맞게 게시물 작성 |  |
| showEditBoard | /board/{id} | GET | 게시글 수정 페이지 반환 | ***수정 필요 해당 유저의 게시물이 맞는지*** |
| editBoard | /board/{id} | PUT | 게시물 수정 |  |
| deleteBoard | /board/{id} | DELETE | 게시물 삭제 |  |

***

## ➰ REPLY
| 📛NAME | 📎URL | ⚙︎METHOD | ❓︎DESCRIPTION | 비고 |
| --- | --- | --- | --- | --- |
| writeReply | /board/reply | POST | 해당 게시물 댓글 달기 | 댓글 깊이 그룹도 같이 |
| updateReply | /board/reply/{replyId} | PUT | 해당 게시물 댓글 수정 | ***수정 필요 해당 유저의 게시물이 맞는지*** |
| deleteReply | /board/reply/{replyId} | DELETE | 해당 게시물 댓글 삭제 | ***수정 필요 해당 유저의 게시물이 맞는지*** |

***

## 🎈 HELP
| 📛NAME | 📎URL | ⚙︎METHOD | ❓︎DESCRIPTION | 비고 |
| --- | --- | --- | --- | --- |
| showQnaEditor | /help | GET | 신고/문의 작성 페이지로 이동 |  |
| writeQuestion | /help/question | POST | 신고/문의 작성 |  |
| findAllQuestionByUser | /help/question/all | GET | 자신이 쓴 전체 질문 조회 |  |
| findInquiryByUser | /help/inquiry | GET | 자신이 쓴 문의글 조회 |  |
| findDeclarationByUser | /help/declaration | GET | 자신이 쓴 신고글 조회 |  |

***

## 🧑 ADMIN
| 📛NAME | 📎URL | ⚙︎METHOD | ❓︎DESCRIPTION | 비고 |
| --- | --- | --- | --- | --- |
| findUserId | /admin/user/{userId} | GET | 유저 검색 |  |
| findAllQuestion | /admin/question | GET | 신고/문의 전체 조회 | ***수정 필요 해당 유저의 게시물이 맞는지*** |
| helpPageCount | /admin/help/helpPageCount | GET | 신고/문의 페이지 카운트 | ***수정 필요 해당 유저의 게시물이 맞는지*** |
| getAllBoard | /admin/board | GET | 게시글 전체 가져오기 | ***정렬, 보드타입, 내용검색, 닉네임검색이 합쳐질 수 있음*** |
| getAllBoardByType | /admin/board/{boardType} | GET | 보드 타입에 따른 게시글 전체 가져오기 |  |
| getAllUser | /admin/user/{pageNum} | GET | 전체 유저 목록 가져오기 |  |
| userPageCount | /admin/user/pageCount | GET | 전체 유저 목록 가져올때 페이징을 위한 API |  |
| userInfo | /admin/user/{userId} | GET | 해당 유저 클릭시 정보 가져오기 | ***최종접속 등 요구사항에 있는 컬럼 추가 예정*** |
| updateUserState | /admin/user/{userId}/userState | PUT | 유저의 상태 변경 |  |
| updateUserRank | /admin/user/{userId}/userRank | PUT | 유저의 등급 변경 |  |
| showAnswerEditor | /admin/answer/{id} | GET | 답변 페이지로 이동 |  |
| updateAnswer | /admin/help/answer/{id} | PUT | 신고/문의 답변 | help api 명세 목록에도 존재 |
| updateBoardState | /admin/board/{boardId} | PUT | 게시글 상태 변경(비공개 처리) |  |
| deleteBoard | /admin/board/{boardId} | DELETE | 게시글 삭제 |  |

***

# 📕 코딩 컨벤션

***

### 커밋 / 코드 컨벤션

### [컨벤션](https://www.notion.so/oreumi/1dd9516322214887a47d186ca80322f3)

***

# 😊 조원

***
- 위영석
- 장윤종
- 정의진
- 최지윤
