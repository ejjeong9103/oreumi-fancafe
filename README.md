# ☯ 흑백엔드

# 📆 프로젝트 진행 기간
***
2024.10.28(월) ~ 2024.11.14(목) (18일간 진행)  
Oreumi 백엔드 6기 2차 프로젝트
***
# 🎈프로젝트 소개
***
임영웅을 사랑하는 팬들이 모여 자유롭게 소통하고 다양한 정보를 공유할 수 있는 커뮤니티 웹사이트입니다. 팬카페 형태의 이 웹사이트는 팬들 간의 교류를 활성화하고, 연예인과 직접적으로 소통할 수 있는 기회를 제공합니다. 팬들은 게시물 작성, 등업 시스템, 정모 일정 확인, 후원 기능 등 다양한 활동을 통해 즐거운 커뮤니티 경험을 할 수 있습니다.

***
# 💜 주요 기능
***
주요기능 내용
***
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
(개발 일정)
***

# 🖨 API 명세
***
(API 명세 내용)
***

# 🖥 시연 영상
***
(시연 영상)
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
