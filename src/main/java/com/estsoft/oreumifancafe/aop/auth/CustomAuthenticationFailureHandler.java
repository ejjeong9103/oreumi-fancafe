package com.estsoft.oreumifancafe.aop.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // JSON 응답 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        exception.printStackTrace();
        System.out.println("Exception type: " + exception.getClass().getName());
        if (exception.getCause() != null) {
            System.out.println("Cause type: " + exception.getCause().getClass().getName());
        }

        String errorMessage;
        if (exception instanceof DisabledException || (exception.getCause() instanceof DisabledException)) {
            errorMessage = "탈퇴한 계정입니다.";
        } else if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "아이디 또는 비밀번호를 확인해주세요.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }

        // 오류 메시지를 JSON 형식으로 반환
        Map<String, String> errorData = new HashMap<>();
        errorData.put("message", errorMessage);

        response.getWriter().write(objectMapper.writeValueAsString(errorData));
    }
}
