package com.estsoft.oreumifancafe.controller.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.board.AddBoardRequest;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.service.board.BoardService;
import com.estsoft.oreumifancafe.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    public BoardController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping("/new-article")
    public String showBoard(@RequestParam(required = false, defaultValue = "0") int boardType, Model model) {
        model.addAttribute("boardType",boardType);
        model.addAttribute("request", new AddBoardRequest());
        return "postEditor";
    }

    @GetMapping("/article/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
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
                           @AuthenticationPrincipal User loggedInUser, // 로그인된 사용자 정보 추가
                           Model model) {
        Board board = boardService.findById(id); // ID를 통해 Board 객체 조회
        User user = board.getUser(); // 게시글 작성자 정보

        System.out.println("게시글 작성자 ID: " + user.getUserId());
        System.out.println("로그인된 사용자 ID: " + (loggedInUser != null ? loggedInUser.getUserId() : "로그인되지 않음"));

        boolean isMyBoard = false;
        if (loggedInUser != null && user.getUserId().equals(loggedInUser.getUserId())) {
            isMyBoard = true;
        }

        model.addAttribute("article", board);
        model.addAttribute("user", user);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("isMyBoard", isMyBoard);
        model.addAttribute("boardType", boardType); // boardType을 모델에 추가하여 템플릿에서 사용 가능

        return "post"; // post.html 렌더링
    }

    @PostMapping
    public String writeBoard(@ModelAttribute AddBoardRequest request,
                             @AuthenticationPrincipal User user,
                             Model model) {

        Board board = boardService.writeBoard(request, user); // 새 게시글 생성 및 저장

        int boardType = board.getBoardType(); // 생성된 게시글의 boardType 가져오기
        return "redirect:/board/article/" + boardType + "/" + board.getId();
    }

    @PutMapping("/article/edit/{id}")
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

        Pageable pageRequest = PageRequest.of(pageNum -1 , 30); // pageNum과 페이지 크기 30 설정

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
        Pageable pageable = PageRequest.of(pageNum -1, 30);
        Page<Board> boardPage = boardService.findBoardByTitle(title,pageable);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("title",title);
        int boardType = boardPage.getContent().get(0).getBoardType();
        model.addAttribute("boardType",boardType);
        return "board_search";
    }

    @GetMapping("/nickname/{nickname}/{pageNum}")
    public String getBoardsByNickname(
            @PathVariable String nickname,
            @PathVariable int pageNum,
            Model model) {
        Pageable pageable = PageRequest.of(pageNum - 1, 30);
        Page<Board> boardPage = boardService.findBoardByNickname(nickname, pageable);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("nickname", nickname);
        int boardType = boardPage.getContent().get(0).getBoardType();
        model.addAttribute("boardType",boardType);
        return "board_search";
    }

    @GetMapping("/keyword/{keyword}/{pageNum}")
    public String getBoardsByKeyword(
            @PathVariable String keyword,
            @PathVariable int pageNum,
            Model model) {
        Pageable pageable = PageRequest.of(pageNum - 1, 30);
        Page<Board> boardPage = boardService.findBoardByKeyword(keyword, pageable);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("keyword", keyword);
        int boardType = boardPage.getContent().get(0).getBoardType();
        model.addAttribute("boardType",boardType);
        return "board_search";
    }
}
