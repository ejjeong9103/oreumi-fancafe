package com.estsoft.oreumifancafe.aop.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // 이미 로그인된 사용자가 접근한 경우
            response.sendRedirect("/accessDenied");  // 권한이 부족한 페이지로 리다이렉트
        } else {
            // 비인증 상태에서 접근한 경우
            response.sendRedirect("/authentication");  // 로그인 하세요!
        }
    }
}
