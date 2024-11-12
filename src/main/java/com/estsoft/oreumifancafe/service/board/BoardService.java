package com.estsoft.oreumifancafe.service.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.admin.BoardResponse;
import com.estsoft.oreumifancafe.domain.dto.board.AddBoardRequest;
import com.estsoft.oreumifancafe.domain.dto.reply.ReplyResponse;
import com.estsoft.oreumifancafe.domain.reply.Reply;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.exceptions.UserNotFoundException;
import com.estsoft.oreumifancafe.repository.board.BoardRepository;
import com.estsoft.oreumifancafe.repository.reply.ReplyRepository;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private static final int PAGE_SIZE = 30;
    private static final int MY_PAGE_SIZE = 3;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
    }

    public Board writeBoard(AddBoardRequest request, User user) {
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .boardType(request.getBoardType())
                .boardCategoryName(request.getBoardCategoryName())
                .state(request.getBoardType() == 1 ? 1: 0)
                .build();

        return boardRepository.save(board);
    }

    public long boardPageCount(int boardType, Pageable pageable) {
        long totalCount = boardRepository.countByBoardType(boardType);
        return (totalCount + pageable.getPageSize() -1 ) / pageable.getPageSize();
    }

    public Page<Board> getAllBoard(int boardType, Pageable pageable) {
        return boardRepository.findAllByBoardType(boardType, pageable);
    }

    public Page<Board> findBoardByTitle(String title, Pageable pageable) {
        return boardRepository.findByTitleContaining(title, pageable);
    }

    public Page<Board> findBoardByNickname(String nickname, Pageable pageable) {
        return boardRepository.findByUserNickname(nickname, pageable);
    }

    public Page<Board> findBoardByKeyword(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없음"));
    }

    public void updateBoard(Long id, AddBoardRequest request, User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 게시글 작성자와 현재 사용자 확인
        if (!board.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        // 게시글 내용 수정
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setBoardType(request.getBoardType());
        board.setBoardCategoryName(request.getBoardCategoryName());

        boardRepository.save(board);
    }

    public int deleteBoard(Long id, User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 작성자와 현재 로그인된 사용자 비교
        if (!board.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }
        int boardType = board.getBoardType();
        boardRepository.deleteById(id);
        return boardType;
    }

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum - 1, pageSize);
    }

    // 해당 유저의 글 목록
    public Page<BoardResponse> findByUserId(User user, int pageNum) {
        return boardRepository.findBoardByUser(user,  createPageRequest(pageNum, MY_PAGE_SIZE)).map(Board::toBoardResponse);
    }

    // 해당 유저가 남긴 댓글의 게시물을 가져오기
    public Page<BoardResponse> findDistinctBoardsByUserComments(User user, int pageNum) {

        Page<Long> boardIds = replyRepository.findDistinctBoardIdsByUser(user, createPageRequest(pageNum, MY_PAGE_SIZE));

        List<Board> boards = boardRepository.findByIdIn(boardIds.getContent());

        // 3. Board 엔터티를 DTO로 변환
        List<BoardResponse> boardResponses = boards.stream()
                .map(Board::toBoardResponse)
                .toList();

        // 4. Page 객체 반환
        return new PageImpl<>(boardResponses, createPageRequest(pageNum, MY_PAGE_SIZE), boardIds.getTotalElements());
    }
}