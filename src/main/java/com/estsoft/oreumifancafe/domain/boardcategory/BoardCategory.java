package com.estsoft.oreumifancafe.domain.boardcategory;

import com.estsoft.oreumifancafe.domain.boardtype.BoardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BoardCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardCategoryName;

    @ManyToOne
    @JoinColumn(name = "board_type_id", nullable = false)
    private BoardType boardType;

    public BoardCategory(String boardCategoryName, BoardType boardType) {
        this.boardCategoryName = boardCategoryName;
        this.boardType = boardType;
    }
}
