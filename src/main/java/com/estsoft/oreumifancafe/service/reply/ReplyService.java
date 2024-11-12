package com.estsoft.oreumifancafe.service.reply;


import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.reply.AddReplyRequest;
import com.estsoft.oreumifancafe.domain.reply.Reply;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.repository.reply.ReplyRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public Reply addReply(AddReplyRequest request, Board board, User user, Long parentReplyId) {
        int hierarchy = 0;
        int orders = 0;
        int group;

        if (parentReplyId != null) {
            // 부모 댓글이 있을 때
            Reply parentReply = replyRepository.findById(parentReplyId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다"));
            hierarchy = parentReply.getHierarchy() + 1;
            group = parentReply.getGroup();
            orders = findNextOrderInGroup(group);
        } else {
            // 새로운 최상위 댓글 (부모 댓글이 없을 때)
            group = replyRepository.findMaxGroup() + 1;
            orders = 0; // 최상위 댓글의 order는 0으로 시작
        }

        // 댓글 엔티티 생성
        Reply reply = new Reply(request.getContent(), hierarchy, orders, group, board, user);
        return replyRepository.save(reply);
    }

    private int findNextOrderInGroup(int group) {
        Integer maxOrder = replyRepository.findMaxOrderInGroup(group);
        return (maxOrder != null ? maxOrder + 1 : 0);
    }

    public List<Reply> getRepliesByBoardId(Long boardId) {
        return replyRepository.findAllByBoardIdOrderByGroupAscOrdersAsc(boardId);
    }

    public void deleteReply(Long replyId, User user) {

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        // 작성자 확인 (또는 관리자 권한 확인)
        if (!reply.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다");
        }

        replyRepository.delete(reply);
    }

    public Reply updateReply(Long replyId, String updatedContent, User user) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다"));

        // 작성자 확인 (또는 관리자 권한 확인)
        if (!reply.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다");
        }

        reply.setContent(updatedContent);
        return replyRepository.save(reply);
    }

}
