package com.estsoft.oreumifancafe.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    // 로그인 후 접근 권한이 없으면 거부
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    // 비 로그인 접근 거부
    @GetMapping("/authentication")
    public String authentication(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        model.addAttribute("user", session.getAttribute("user"));
        return "authentication";
    }
}
