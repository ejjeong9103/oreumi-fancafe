package com.estsoft.oreumifancafe.repository.board;

import com.estsoft.oreumifancafe.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByUserUserId(String userId);
}
