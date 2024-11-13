package com.estsoft.oreumifancafe.aop.auth;

import com.estsoft.oreumifancafe.domain.reply.Reply;
import com.estsoft.oreumifancafe.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CustomAuthorizationReplyManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final ReplyRepository replyRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();
        String replyIdStr = context.getVariables().get("replyId") == null ? context.getVariables().get("boardId").toString() : context.getVariables().get("replyId").toString();
        long replyId = Long.parseLong(replyIdStr);
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 인증 객체에서 유저 아이디 확인
        if (!reply.getUser().getUserId().equals(auth.getName())) {
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }
}
