package com.estsoft.oreumifancafe.aop.auth;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CustomAuthorizationBoardManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final BoardService boardService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();
        long boardId = Long.parseLong(context.getVariables().get("id"));
        Board board = boardService.findById(boardId);

        // 인증 객체에서 유저 아이디 확인
        if (!board.getUser().getUserId().equals(auth.getName())) {
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }
}
