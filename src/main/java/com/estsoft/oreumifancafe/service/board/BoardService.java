package com.estsoft.oreumifancafe.service.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.dto.board.AddBoardRequest;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.repository.board.BoardRepository;
import com.estsoft.oreumifancafe.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Board createBoard(AddBoardRequest request, User user) {
        Board board;
        if(request.getBoardType() == 1 ) { // 등업게시판만 말머리를 쓰니까 등업게시판의 boardType이 1로 설정된 경우
            board = Board.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .user(user)
                    .boardType(request.getBoardType())
                    .boardCategoryName(request.getBoardCategoryName())
                    .state(1)
                    .build();
        } else {
            board = Board.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .user(user)
                    .boardType(request.getBoardType())
                    .boardCategoryName(null)
                    .state(0)
                    .build();
        }
        return boardRepository.save(board);
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> findBoardById(String userId) {
        return boardRepository.findByUserUserId(userId);
    }

    public void deleteBy(Long id) {
        boardRepository.deleteById(id);
    }


}


// 게시판 목록 페이지로 가는 기능
//-> 유저랑 페이지를 가라데이터를 넣는다
//->
// 작성 -> 포스트맵핑 리퀘스트 dto 받아서
//