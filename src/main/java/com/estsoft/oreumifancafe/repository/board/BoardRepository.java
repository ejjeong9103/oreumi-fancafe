package com.estsoft.oreumifancafe.repository.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Page<Board> findByTitleContaining(String title, Pageable pageable);
    Page<Board> findByUserNickname(String nickname, Pageable pageable);
    Page<Board> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
    Page<Board> findAllByBoardType(int boardType, Pageable pageable);
    long countByBoardType(int boardType);
    List<Board> findBoardByUser(User user, Pageable pageable);
}