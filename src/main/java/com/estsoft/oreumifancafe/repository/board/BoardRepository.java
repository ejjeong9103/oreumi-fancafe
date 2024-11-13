package com.estsoft.oreumifancafe.repository.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import com.estsoft.oreumifancafe.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Page<Board> findByTitleContaining(String title, Pageable pageable);
    Page<Board> findByUserNickname(String nickname, Pageable pageable);
    Page<Board> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
    Page<Board> findAllByBoardType(int boardType, Pageable pageable);
    long countByBoardType(int boardType);
    Optional<Board> findAllByBoardType(int boardType);
    Page<Board> findBoardByUserOrderByIdDesc(User user, Pageable pageable);

    Page<Board> findBoardByBoardType(int boardType, Pageable pageable);
    Page<Board> findBoardByBoardTypeAndUserUserIdContaining(int boardType, String userId, Pageable pageable);
    Page<Board> findBoardByBoardTypeAndUserNicknameContaining(int boardType, String nickname, Pageable pageable);
    Page<Board> findBoardByBoardTypeAndTitleContaining(int boardType, String title, Pageable pageable);
    Page<Board> findBoardByBoardTypeAndContentContaining(int boardType, String content, Pageable pageable);
    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword%)")
    Page<Board> findBoardByBoardTypeAndTitleOrContent(
            @Param("boardType") int boardType,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}