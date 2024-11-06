package com.estsoft.oreumifancafe.aop;

import com.estsoft.oreumifancafe.domain.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        // Authentication 객체에서 사용자 정보 가져오기
        User user = (User) authentication.getPrincipal(); // CustomUserDetailsService에서 반환한 UserDetails 타입이 User라면

        // 세션에 사용자 정보 저장
        session.setAttribute("user", user);

        // 로그인 후 메인 페이지로 리다이렉트
        response.sendRedirect("/");
    }
}
