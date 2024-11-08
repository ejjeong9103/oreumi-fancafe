package com.estsoft.oreumifancafe.controller.user;

import com.estsoft.oreumifancafe.domain.dto.admin.BoardResponse;
import com.estsoft.oreumifancafe.domain.dto.user.AddUserRequest;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.service.board.BoardService;
import com.estsoft.oreumifancafe.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BoardService boardService;

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup() {
        return "registerPage";
    }

    // 회원가입
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@ModelAttribute AddUserRequest addUserRequest) {
        userService.saveUser(addUserRequest);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // userId 중복 체크
    @GetMapping("/check/userid/{userId}")
    @ResponseBody // JSON 응답만 필요한 메서드에 @ResponseBody 추가
    public ResponseEntity<Boolean> checkUserId(@PathVariable String userId) {
        return ResponseEntity.ok(userService.isDuplicateUserId(userId));
    }

    // nickname 중복 체크
    @GetMapping("/check/nickname/{nickname}")
    @ResponseBody // JSON 응답만 필요한 메서드에 @ResponseBody 추가
    public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.isDuplicateNickName(nickname));
    }

    // email 중복 체크
    @GetMapping("/check/email/{email}")
    @ResponseBody // JSON 응답만 필요한 메서드에 @ResponseBody 추가
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.isDuplicateEmail(email));
    }

    // 마이페이지
    @GetMapping("/myPage")
    public String myPage(HttpServletRequest request, Model model,
                         @RequestParam(defaultValue = "1") int boardPageNum,
                         @RequestParam(defaultValue = "1") int replyPageNum,
                         @RequestParam(defaultValue = "1") int qaPageNum,
                         @RequestParam(defaultValue = "1") int reportPageNum) {
        // 세션에 있는 유저를 꺼내옴
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 내 글 목록
        Page<BoardResponse> boardResponseList = boardService.findByUserId(user, boardPageNum);

        model.addAttribute("myBoard", boardResponseList);
        return "myPage";
    }

    // 회원정보수정 페이지 이동
    @GetMapping("/updateInfo")
    public String updateInfoPage() {
        return "editProfile";
    }

    // 회원정보 수정
    @PutMapping("/updateInfo")
    @ResponseBody
    public ResponseEntity updateInfo(@ModelAttribute AddUserRequest addUserRequest,
                             HttpServletRequest request) {
        // 세션의 유저 아이디와 전달받은 유저의 아이디가 같다면
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("세션 유저가 존재하지 않습니다.");
        } else if (!sessionUser.getUserId().equals(addUserRequest.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("권한이 없습니다.");
        } else {
            // 회원 정보 수정
            // 세션 업데이트
            session.setAttribute("user", userService.updateUser(addUserRequest));
            return ResponseEntity.ok("회원정보 수정이 완료되었습니다.");
        }
    }
}