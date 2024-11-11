package com.estsoft.oreumifancafe.aop.auth;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();
        String userId = context.getVariables().get("userId").toString();

        // 인증 객체에서 유저 아이디 확인
        if (!userId.equals(auth.getName())) {
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }
}