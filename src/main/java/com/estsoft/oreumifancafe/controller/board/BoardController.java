package com.estsoft.oreumifancafe.controller.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.board.AddBoardRequest;
import com.estsoft.oreumifancafe.domain.reply.Reply;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.service.board.BoardService;
import com.estsoft.oreumifancafe.service.reply.ReplyService;
import com.estsoft.oreumifancafe.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final ReplyService replyService;

    public BoardController(BoardService boardService, UserService userService, ReplyService replyService) {
        this.boardService = boardService;
        this.userService = userService;
        this.replyService = replyService;
    }

    @GetMapping("/new-article")
    public String showBoard(@RequestParam(required = false, defaultValue = "0") int boardType, Model model) {
        model.addAttribute("boardType",boardType);
        model.addAttribute("request", new AddBoardRequest());
        return "postEditor";
    }

    @GetMapping("/{id}")
    public String showEditBoard(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id); // ID를 통해 Board 객체 조회
        model.addAttribute("board", board);
        model.addAttribute("request", new AddBoardRequest(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getBoardType(),
                board.getBoardCategoryName()
        ));
        return "postEditor";
    }

    @GetMapping("/article/{boardType}/{id}")
    public String viewPost(@PathVariable int boardType,
                           @PathVariable Long id,
                           @AuthenticationPrincipal User loggedInUser,
                           Model model) {
        Board board = boardService.findById(id);
        User user = board.getUser();
        List<Reply> comments = replyService.getRepliesByBoardId(id);

        boolean isMyBoard = loggedInUser != null && user.getUserId().equals(loggedInUser.getUserId());

        model.addAttribute("article", board);
        model.addAttribute("user", user);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("isMyBoard", isMyBoard);
        model.addAttribute("boardType", boardType);
        model.addAttribute("comment", comments);

        return "post"; // post.html 렌더링
    }

    @PostMapping
    public String writeBoard(@ModelAttribute AddBoardRequest request,
                             @AuthenticationPrincipal User user) {

        Board board = boardService.writeBoard(request, user); // 새 게시글 생성 및 저장

        int boardType = board.getBoardType(); // 생성된 게시글의 boardType 가져오기
        return "redirect:/board/article/" + boardType + "/" + board.getId();
    }

    @PutMapping("/{id}")
    public String editBoard(@PathVariable Long id,
                            @ModelAttribute AddBoardRequest request,
                            @AuthenticationPrincipal User user,
                            Model model) {

        // 서비스 로직을 호출하여 게시글을 수정
        boardService.updateBoard(id, request, user);

        // 수정된 게시글의 boardType과 id를 가져와서 리디렉션
        return "redirect:/board/article/" + request.getBoardType() + "/" + request.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id, @AuthenticationPrincipal User user) {
        int boardType = boardService.deleteBoard(id, user);
        return "redirect:/board/" + boardType + "/1";
    }

    // 게시글 페이징에 필요한 페이지 수 반환
    @GetMapping("/{boardType}/pagecount")
    public long getBoardPageCount(@PathVariable int boardType, @PageableDefault(size =30) Pageable pageable) {
        return boardService.boardPageCount(boardType, pageable);
    }

    // 페이징하여 게시물 타입에 따른 게시물 목록 조회
    @GetMapping("/{boardType}/{pageNum}")
    public String getAllBoard(
            @PathVariable int boardType,
            @PathVariable int pageNum,
            Model model) {

        Pageable pageRequest = PageRequest.of(pageNum -1 , 30, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage = boardService.getAllBoard(boardType, pageRequest);

        model.addAttribute("boardPage", boardPage); // 페이징된 게시글 데이터를 뷰로 전달
        model.addAttribute("boardType", boardType); // 현재 게시물 타입도 전달

        return "board";
    }

    @GetMapping("/title/{title}/{pageNum}")
    public String getBoardsByTitle(
            @PathVariable String title,
            @PathVariable int pageNum,
            Model model) {

        Pageable pageable = PageRequest.of(pageNum -1, 30, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage = boardService.findBoardByTitle(title,pageable);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("title", title);

        return "board_search";
    }

    @GetMapping("/nickname/{nickname}/{pageNum}")
    public String getBoardsByNickname(
            @PathVariable String nickname,
            @PathVariable int pageNum,
            Model model) {

        Pageable pageable = PageRequest.of(pageNum - 1, 30, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage = boardService.findBoardByNickname(nickname, pageable);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("nickname", nickname);

        return "board_search";
    }

    @GetMapping("/keyword/{keyword}/{pageNum}")
    public String getBoardsByKeyword(
            @PathVariable String keyword,
            @PathVariable int pageNum,
            Model model) {

        Pageable pageable = PageRequest.of(pageNum - 1, 30, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage = boardService.findBoardByKeyword(keyword, pageable);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("keyword", keyword);

        return "board_search";
    }
}
