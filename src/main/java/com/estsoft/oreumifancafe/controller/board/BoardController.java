package com.estsoft.oreumifancafe.controller.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.board.AddBoardRequest;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.service.board.BoardService;
import com.estsoft.oreumifancafe.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/new")
    public String showBoard() {
        return "postEditor";
    }

    @PostMapping
    public ResponseEntity<String> createBoard(@RequestBody AddBoardRequest request,@RequestParam String userId) {
        User user = userService.findUserById(userId);
        boardService.createBoard(request, user);
        return ResponseEntity.ok("게시물이 성공적으로 생성되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<Board>> getBoardList() {
        List<Board> boards = boardService.findAllBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Board>> getBoardsByUserId(@PathVariable String userId) {
        List<Board> boards = boardService.findBoardById(userId);
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {

        boardService.deleteBy(id);
        return ResponseEntity.ok().build();
    }

}
