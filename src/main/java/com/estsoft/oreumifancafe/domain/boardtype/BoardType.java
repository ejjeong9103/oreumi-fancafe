package com.estsoft.oreumifancafe.domain.boardtype;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private int boardType;

    public BoardType(String boardName, int boardType) {
        this.boardName = boardName;
        this.boardType = boardType;
    }
}
