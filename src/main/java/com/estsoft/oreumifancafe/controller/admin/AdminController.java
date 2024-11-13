package com.estsoft.oreumifancafe.controller.admin;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.admin.BoardResponse;
import com.estsoft.oreumifancafe.domain.dto.admin.UpdateBoardStateRequest;
import com.estsoft.oreumifancafe.domain.dto.admin.UpdateStateRequest;
import com.estsoft.oreumifancafe.domain.dto.admin.UserInfoResponse;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public String getAdminPage() {
        return "/admin/adminIndex";
    }

    @GetMapping("/user")
    public String getUserPage(Model model,
                              @RequestParam(defaultValue = "1") int userPageNum,
                              @RequestParam(defaultValue = "") String nickname,
                              @RequestParam(defaultValue = "") String userId) {
        Page<UserInfoResponse> userInfoResponses = adminService.getUserPaging(userPageNum, userId, nickname);
        model.addAttribute("user", userInfoResponses);
        model.addAttribute("userId", userId);
        model.addAttribute("nickname", nickname);
        return "/admin/adminUser";
    }

    // 리팩토링 해야할 부분
    // 파라미터가 너무 많음
    // 급하게 만드느냐 파라미터로 분기
    // 처리중 처리상태나 정렬등 추가하려면 spec이나 쿼리dsl로 추후에 변경.....
    @GetMapping("/board")
    public String getUserPage(Model model,
                              @RequestParam(defaultValue = "1") int boardPageNum,
                              @RequestParam(defaultValue = "1") int boardType,
                              @RequestParam(defaultValue = "") String title,
                              @RequestParam(defaultValue = "") String content,
                              @RequestParam(defaultValue = "") String titleOrContent,
                              @RequestParam(defaultValue = "") String userId,
                              @RequestParam(defaultValue = "") String nickname) {
        Page<Board> boardPage = adminService.getBoardPaging(boardPageNum, boardType, userId, nickname, title, content, titleOrContent);
        model.addAttribute("board", boardPage);
        model.addAttribute("userId", userId);
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("titleOrContent", titleOrContent);
        model.addAttribute("nickname", nickname);
        model.addAttribute("boardType", boardType);
        return "/admin/adminBoard";
    }

    @GetMapping("/board/state")
    public String boardStateUpdate(@RequestParam long boardId,
                                   @RequestParam int state) {
        adminService.updateBoardState(boardId, state);
        return "redirect:/admin/board";
    }

    @PutMapping("/board/hidden")
    public ResponseEntity boardHiddenUpdate(@RequestParam long boardId,
                                   @RequestParam int hidden) {
        adminService.updateBoardHidden(boardId, hidden);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "처리가 완료되었습니다.");
        response.put("boardId", boardId);
        response.put("hidden", hidden);

        return ResponseEntity.ok(response);
    }

    // 사용자 ID 조회
    @GetMapping("/user/{userId}")
    public String findUserById(@PathVariable String userId, Model model) {
        UserInfoResponse user = adminService.findUserById(userId);

        model.addAttribute("user", user);

        return "adminPage";
    }

    // 모든 사용자 조회
//    @GetMapping("/user")
//    public String getAllUser(Model model) {
//        List<UserInfoResponse> users = adminService.getAllUser();
//
//        model.addAttribute("userList", users);
//
//        return "adminPage";
//    }

    // 신고 및 문의 전체 조회
    @GetMapping("/help/{userId}")
    public String getAllHelp(@PathVariable String userId, Model model) {
        List<HelpResponse> helps = adminService.getAllHelp(userId);

        model.addAttribute("helpList", helps);

        return "adminPage";
    }

    // 게시글 조회
//    @GetMapping("/board")
//    public String getBoards(@RequestParam(required = false) Integer boardType, Model model) {
//        List<BoardResponse> boards;
//
//        if (boardType == null) {
//            boards = adminService.getAllBoard();
//        }
//        else {
//            boards = adminService.getAllBoardByType(boardType);
//        }
//
//        model.addAttribute("boardList", boards);
//
//        return "adminPage";
//    }

    // 사용자 상태 변경
    @PutMapping("/user/{userId}/userState")
    public ResponseEntity<UserResponse> updateUserState(@PathVariable String userId,
                                                        @RequestBody UpdateStateRequest request) {
        return ResponseEntity.ok(adminService.updateUserState(userId, request));
    }

    // 게시글 상태 변경 (게시글 비공개 처리)
    @PutMapping("/board/{boardId}")
    public ResponseEntity<BoardResponse> updateBoardState(@PathVariable Long id,
                                                          @RequestBody UpdateBoardStateRequest request) {
        return ResponseEntity.ok(adminService.updateBoardState(id, request));
    }

    // 게시글 삭제
    @DeleteMapping("board/deleteBoard/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boolean valid = adminService.deleteBoard(id);

        if (valid == false) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
