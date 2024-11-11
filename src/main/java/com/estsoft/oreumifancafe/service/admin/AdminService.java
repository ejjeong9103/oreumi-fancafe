package com.estsoft.oreumifancafe.service.admin;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.admin.*;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.exceptions.UserNotFoundException;
import com.estsoft.oreumifancafe.repository.board.BoardRepository;
import com.estsoft.oreumifancafe.repository.help.HelpRepository;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final HelpRepository helpRepository;
    private final BoardRepository boardRepository;

    // 사용자 ID 조회
    public UserResponse findUserById(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .toUserResponse();
    }

    // 신고 및 문의 전체 조회
    public List<HelpResponse> getAllHelp(String userId) {
        return helpRepository.findByUserId(userId).stream()
                .map(HelpResponse::new)
                .toList();
    }

    // 신고 및 문의 페이지 카운트
    public Long helpPageCount(String userId) {
        List<HelpResponse> helpList = getAllHelp(userId);

        return (long) helpList.size();
    }

    // 게시글 전체 조회
    public List<BoardResponse> getAllBoard() {
        List<BoardResponse> list = boardRepository.findAll().stream()
                .map(Board::toBoardResponse)
                .toList();

        return list;
    }

    // 특정 게시판 게시글 조회
    public List<BoardResponse> getAllBoardByType(int boardType) {
        List<BoardResponse> list = boardRepository.findAllByBoardType(boardType).stream()
                .map(Board::toBoardResponse)
                .toList();

        return list;
    }

    // 모든 사용자 조회
    public List<UserResponse> getAllUser() {
        List<UserResponse> list = userRepository.findAll().stream()
                .map(User::toUserResponse)
                .toList();

        return list;
    }
    
    // 사용자 정보 조회
    public UserInfoResponse userInfo(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .toUserInfoResponse();
    }

    // 사용자 상태 변경
    @Transactional
    public UserResponse updateUserState(String userId, UpdateStateRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.updateState(request.getState());

        return user.toUserResponse();
    }

    // 사용자 등급 변경
//    @Transactional
//    public UserResponse updateUserRank(String userId, UpdateRoleRequest request) {
//        User user = userRepository.findByUserId(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId));
//
//        user.updateRole(request.getRole());
//
//        return user.toUserResponse();
//    }

    // 신고 및 문의 사항 답변

    // 게시글 상태 변경 (게시글 비공개 처리)
    @Transactional
    public BoardResponse updateBoardState(Long id, UpdateBoardStateRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + ""));

        board.updateState(request.getState());

        return board.toBoardResponse();
    }

    // 게시글 삭제
    public boolean deleteBoard(Long id) {
        if (boardRepository.findById(id) == null) {
            return false;
        }

        boardRepository.deleteById(id);

        return true;
    }
}