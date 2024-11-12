package com.estsoft.oreumifancafe.controller.reply;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.reply.AddReplyRequest;
import com.estsoft.oreumifancafe.domain.reply.Reply;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.service.board.BoardService;
import com.estsoft.oreumifancafe.service.reply.ReplyService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class ReplyController {
    private final ReplyService replyService;
    private final BoardService boardService;

    public ReplyController(ReplyService replyService, BoardService boardService) {
        this.replyService = replyService;
        this.boardService = boardService;
    }

    @PostMapping("/article/{boardId}/reply")
    public String addReply(@PathVariable Long boardId,
                           @RequestParam String commentbox,
                           @RequestParam(required = false) Long parentReplyId,
                           @AuthenticationPrincipal User loggedInUser,
                           Model model) {
        // 게시글 조회
        Board board = boardService.findById(boardId);

        // 댓글 작성 요청 객체 생성
        AddReplyRequest request = new AddReplyRequest();
        request.setContent(commentbox);

        // 댓글 작성
        replyService.addReply(request, board, loggedInUser, parentReplyId);

        // 댓글 리스트를 model에 추가
        List<Reply> comments = replyService.getRepliesByBoardId(boardId);
        model.addAttribute("comment", comments);

        // 작성 후 해당 게시글로 리디렉션
        return "redirect:/board/article/" + board.getBoardType() + "/" + boardId;
    }

    @DeleteMapping("/article/{boardId}/reply/{replyId}")
    public String deleteReply(@PathVariable Long boardId,
                              @PathVariable Long replyId,
                              @AuthenticationPrincipal User loggedInUser) {
        // 댓글 삭제 로직 실행
        replyService.deleteReply(replyId, loggedInUser);

        // boardId를 사용해 boardType 조회
        Board board = boardService.findById(boardId);

        // boardType과 boardId를 포함하여 리다이렉트 경로 반환
        return "redirect:/board/article/" + board.getBoardType() + "/" + boardId;
    }

    @PostMapping("/article/{boardId}/reply/update/{replyId}")
    public String updateReply(@PathVariable Long boardId,
                              @PathVariable Long replyId,
                              @RequestParam("updatedContent") String updatedContent,
                              @AuthenticationPrincipal User loggedInUser) {
        System.out.println("Updated Content: " + updatedContent); // 디버그용
        replyService.updateReply(replyId, updatedContent, loggedInUser);
        Board board = boardService.findById(boardId);
        return "redirect:/board/article/" + board.getBoardType() + "/" + boardId;
    }

}