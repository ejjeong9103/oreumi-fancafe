package com.estsoft.oreumifancafe.controller.user;

import com.estsoft.oreumifancafe.domain.dto.user.AddUserRequest;
import com.estsoft.oreumifancafe.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
