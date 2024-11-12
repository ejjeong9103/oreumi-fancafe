package com.estsoft.oreumifancafe.controller.admin;

import com.estsoft.oreumifancafe.domain.dto.admin.BoardResponse;
import com.estsoft.oreumifancafe.domain.dto.admin.UpdateBoardStateRequest;
import com.estsoft.oreumifancafe.domain.dto.admin.UpdateStateRequest;
import com.estsoft.oreumifancafe.domain.dto.admin.UserInfoResponse;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {
    private final AdminService adminService;

    // 사용자 ID 조회
    @GetMapping("/user/{userId}")
    public String findUserById(@PathVariable String userId, Model model) {
        UserInfoResponse user = adminService.findUserById(userId);

        model.addAttribute("user", user);

        return "adminPage";
    }

    // 모든 사용자 조회
    @GetMapping("/user")
    public String getAllUser(Model model) {
        List<UserInfoResponse> users = adminService.getAllUser();

        model.addAttribute("userList", users);

        return "adminPage";
    }

    // 신고 및 문의 전체 조회
    @GetMapping("/help/{userId}")
    public String getAllHelp(@PathVariable String userId, Model model) {
        List<HelpResponse> helps = adminService.getAllHelp(userId);

        model.addAttribute("helpList", helps);

        return "adminPage";
    }

    // 게시글 조회
    @GetMapping("/board")
    public String getBoards(@RequestParam(required = false) Integer boardType, Model model) {
        List<BoardResponse> boards;

        if (boardType == null) {
            boards = adminService.getAllBoard();
        }
        else {
            boards = adminService.getAllBoardByType(boardType);
        }

        model.addAttribute("boardList", boards);

        return "adminPage";
    }

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
    @DeleteMapping("board/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boolean valid = adminService.deleteBoard(id);

        if (valid == false) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
