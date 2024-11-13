package com.estsoft.oreumifancafe.service.admin;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.admin.*;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.domain.user.Role;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.exceptions.UserNotFoundException;
import com.estsoft.oreumifancafe.repository.board.BoardRepository;
import com.estsoft.oreumifancafe.repository.help.HelpRepository;
import com.estsoft.oreumifancafe.repository.user.RoleRepository;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final HelpRepository helpRepository;
    private final BoardRepository boardRepository;
    private final RoleRepository roleRepository;
    private static final int PAGE_SIZE = 3;

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum - 1, pageSize);
    }

    // 유저 가져오기
    public Page<UserInfoResponse> getUserPaging(int pageNum, String userId, String nickName) {
        Pageable pageable = createPageRequest(pageNum, PAGE_SIZE);
        if (!userId.isEmpty()) {
            return userRepository.findUserByUserIdContaining(userId, pageable).map(User::toUserInfoResponse);
        } else if (!nickName.isEmpty()) {
            return userRepository.findUserByNicknameContaining(nickName, pageable).map(User::toUserInfoResponse);
        } else {
            return userRepository.findAll(pageable).map(User::toUserInfoResponse);
        }
    }

    // 게시글 가져오기
    public Page<Board> getBoardPaging(int boardPageNum,
                                      int boardType,
                                      String userId,
                                      String nickName,
                                      String title,
                                      String content,
                                      String titleOrContent) {
        Pageable pageable = createPageRequest(boardPageNum, PAGE_SIZE);
        if (!userId.isEmpty()) {
            return boardRepository.findBoardByBoardTypeAndUserUserIdContaining(boardType, userId, pageable);
        } else if (!nickName.isEmpty()) {
            return boardRepository.findBoardByBoardTypeAndUserNicknameContaining(boardType, nickName, pageable);
        } else if (!title.isEmpty()) {
            return boardRepository.findBoardByBoardTypeAndTitleContaining(boardType, title, pageable);
        } else if (!content.isEmpty()) {
            return boardRepository.findBoardByBoardTypeAndContentContaining(boardType, content, pageable);
        } else if (!titleOrContent.isEmpty()) {
            return boardRepository.findBoardByBoardTypeAndTitleOrContent(boardType, titleOrContent, pageable);
        } else {
            return boardRepository.findBoardByBoardType(boardType, pageable);
        }
    }

    // 게시글 상태 변경
    public void updateBoardState(long boardId, int state) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없음"));

        board.setState(state);

        // state가 2가 되면 정회원으로 올려주자
        if (state == 2) {
            User user = board.getUser();
            // 사용자 권한 목록을 가져옵니다.
            Collection<Role> roles = user.getRoles();

            // 정회원 권한(ROLE_USER)이 있는지 확인
            boolean hasRoleUser = roles.stream()
                    .anyMatch(role -> "ROLE_USER".equals(role.getName()));

            // 정회원 권한이 없으면 추가
            if (!hasRoleUser) {
                // 권한 추가
                user.getRoles().add(roleRepository.findByName("ROLE_USER"));
                userRepository.save(user);
            }
        }
        boardRepository.save(board);
    }

    // 게시글 숨김 처리
    public void updateBoardHidden(long boardId, int hidden) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없음"));

        board.setHidden(hidden);
        boardRepository.save(board);
    }











    // 사용자 ID 조회
    public UserInfoResponse findUserById(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .toUserInfoResponse();
    }

    // 모든 사용자 조회
    public List<UserInfoResponse> getAllUser() {
        return userRepository.findAll().stream()
                .map(User::toUserInfoResponse)
                .toList();
    }

    // 사용자 정보 조회
//    public UserInfoResponse userInfo(String userId) {
//        return userRepository.findByUserId(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId))
//                .toUserInfoResponse();
//    }

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
