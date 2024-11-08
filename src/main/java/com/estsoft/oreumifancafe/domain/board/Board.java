package com.estsoft.oreumifancafe.domain.board;


import com.estsoft.oreumifancafe.domain.dto.admin.BoardResponse;
import com.estsoft.oreumifancafe.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int boardType;

    @Column
    private String boardCategoryName;

    @Column
    private int state = 0;


    @Builder
    public Board(String title, String content, User user, int boardType, String boardCategoryName, int state) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.boardType = boardType;
        this.boardCategoryName = boardCategoryName;
        this.state = state;
    }

    // 게시판 상태 변경
    public void updateState(int state) {
        this.state = state;
    }

    public BoardResponse toBoardResponse() {
        return BoardResponse.builder()
                .id(this.id)
                .title(this.title)
                .user(this.user)
                .boardType(this.boardType)
                .boardCategoryName(this.boardCategoryName)
                .state(this.state)
                .createdAt(this.createdAt)
                .build();
    }
  
    public Board() {
        this.createdAt = LocalDateTime.now();
    }
}