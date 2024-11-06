package com.estsoft.oreumifancafe.service.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.board.AddBoardRequest;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.repository.board.BoardRepository;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private static final int PAGE_SIZE = 30;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
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

//    public void deleteBy(Long id) {
//        if (!boardRepository.existsById(id)) {
//            throw new IllegalArgumentException("게시물이 없음");
//        }
//        boardRepository.deleteById(id);
//    }
}