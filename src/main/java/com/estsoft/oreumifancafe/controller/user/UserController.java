package com.estsoft.oreumifancafe.controller.user;

import com.estsoft.oreumifancafe.domain.dto.admin.BoardResponse;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.dto.user.AddUserRequest;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.exceptions.UnauthorizedException;
import com.estsoft.oreumifancafe.service.board.BoardService;
import com.estsoft.oreumifancafe.service.help.HelpService;
import com.estsoft.oreumifancafe.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final HelpService helpService;

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser != null) {
            throw new UnauthorizedException("로그인 상태에서는 회원가입 진행이 불가능합니다.");
        } else {
            return "registerPage";
        }
    }

    // 회원가입
    @PostMapping
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
    @GetMapping("/{userId}")
    public String myPage(HttpServletRequest request, Model model,
                         @PathVariable String userId,
                         @RequestParam(defaultValue = "1") int boardPageNum,
                         @RequestParam(defaultValue = "1") int replyPageNum,
                         @RequestParam(defaultValue = "1") int qaPageNum,
                         @RequestParam(defaultValue = "1") int reportPageNum) {
        User user = userService.findUserById(userId);
        // 내 글 목록
        Page<BoardResponse> boardResponseList = boardService.findByUserId(user, boardPageNum);
        Page<BoardResponse> replyBoardResponseList = boardService.findDistinctBoardsByUserComments(user, replyPageNum);
        Page<HelpResponse> qaList = helpService.findByUserAndHelpType(user, qaPageNum, 1);
        Page<HelpResponse> reportList = helpService.findByUserAndHelpType(user, reportPageNum, 2);
        model.addAttribute("myBoard", boardResponseList);
        model.addAttribute("myReply", replyBoardResponseList);
        model.addAttribute("myQa", qaList);
        model.addAttribute("myReport", reportList);
        return "myPage";
    }

    // 회원정보수정 페이지 이동
    @GetMapping("/updateInfo")
    public String updateInfoPage() {
        return "editProfile";
    }

    // 회원정보 수정
    @PutMapping("/{userId}")
    @ResponseBody
    public ResponseEntity updateInfo(@PathVariable String userId
            , @ModelAttribute AddUserRequest addUserRequest,
                                     HttpServletRequest request) {
        // 세션의 유저 아이디와 전달받은 유저의 아이디가 같다면
        HttpSession session = request.getSession();
        // 회원 정보 수정
        // 세션 업데이트
        session.setAttribute("user", userService.updateUser(addUserRequest, userId));
        return ResponseEntity.ok("회원정보 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity deleteUser(@PathVariable String userId,
                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 회원 탈퇴
        userService.deleteUser(userId);
        // 세션 삭제
        session.invalidate();
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 세션 삭제
        session.invalidate();
        return "redirect:/";
    }
}