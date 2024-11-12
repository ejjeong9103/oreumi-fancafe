package com.estsoft.oreumifancafe.domain.reply;

import com.estsoft.oreumifancafe.domain.board.Board;
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
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int hierarchy; // 깊이

    @Column(nullable = false)
    private int orders; // 순서

    @Column(name = "`group`", nullable = false)
    private int group; // 댓글그룹

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Reply(String content, int hierarchy, int orders, int group, Board board, User user) {
        this.content = content;
        this.hierarchy = hierarchy;
        this.orders = orders;
        this.group = group;
        this.board = board;
        this.user = user;
    }
}
