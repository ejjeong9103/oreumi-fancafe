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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {
    private final AdminService adminService;

    // 사용자 ID 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable String userId) {
        return ResponseEntity.ok(adminService.findUserById(userId));
    }

    // 신고 및 문의 전체 조회
    @GetMapping("/help/{userId}")
    public ResponseEntity<List<HelpResponse>> getAllHelp(@PathVariable String userId) {
        return ResponseEntity.ok(adminService.getAllHelp(userId));
    }

    // 신고 및 문의 페이지 카운트
    @GetMapping("/help/{userId}/helpPageCount")
    public ResponseEntity<Long> helpPageCount(@PathVariable String userId) {
        return ResponseEntity.ok(adminService.helpPageCount(userId));
    }

    // 게시글 전체 조회
    @GetMapping("/board/allBoards")
    public ResponseEntity<List<BoardResponse>> getAllBoard() {
        return ResponseEntity.ok(adminService.getAllBoard());
    }

    // 특정 게시판 게시글 조회
    @GetMapping("/board/{boardType}")
    public ResponseEntity<List<BoardResponse>> getAllBoardByType(@PathVariable int boardType) {
        return ResponseEntity.ok(adminService.getAllBoardByType(boardType));
    }

    // 모든 사용자 조회
    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return ResponseEntity.ok(adminService.getAllUser());
    }

    // 사용자 정보 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserInfoResponse> userInfo(@PathVariable String userId) {
        return ResponseEntity.ok(adminService.userInfo(userId));
    }

    // 사용자 상태 변경
    @PutMapping("/user/{userId}/userState")
    public ResponseEntity<UserResponse> updateUserState(@PathVariable String userId,
                                                        @RequestBody UpdateStateRequest request) {
        return ResponseEntity.ok(adminService.updateUserState(userId, request));
    }

    // 신고 및 문의 사항 답변

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
