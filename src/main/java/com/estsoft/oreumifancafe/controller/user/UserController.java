package com.estsoft.oreumifancafe.controller.user;

import com.estsoft.oreumifancafe.domain.dto.user.AddUserRequest;
import com.estsoft.oreumifancafe.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup() {
        return "registerPage";
    }

    // 회원가입
    @PostMapping("/signup")
    public String signup(@ModelAttribute AddUserRequest addUserRequest) {
        userService.saveUser(addUserRequest);
        return "index";
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
}
