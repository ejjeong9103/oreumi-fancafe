package com.estsoft.oreumifancafe.repository.reply;

import com.estsoft.oreumifancafe.domain.reply.Reply;
import com.estsoft.oreumifancafe.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // 댓글 중 가장 큰 group 값을 반환하여 새로운 group ID를 생성할 때 사용
    @Query("SELECT COALESCE(MAX(r.group), 0) FROM Reply r")
    int findMaxGroup();

    // 특정 group 내에서 가장 큰 orders 값을 반환하여 새로운 댓글의 순서를 정할 때 사용
    @Query("SELECT COALESCE(MAX(r.orders), 0) FROM Reply r WHERE r.group = :group")
    int findMaxOrderInGroup(@Param("group") int group);

    @Query("SELECT r FROM Reply r WHERE r.board.id = :boardId ORDER BY r.group ASC, r.orders ASC")
    List<Reply> findAllByBoardIdOrderByGroupAscOrdersAsc(@Param("boardId") Long boardId);

    @Query("SELECT DISTINCT r.board.id FROM Reply r WHERE r.user = :user")
    Page<Long> findDistinctBoardIdsByUser(@Param("user") User user, Pageable pageable);
}
