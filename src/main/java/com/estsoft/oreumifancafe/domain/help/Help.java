package com.estsoft.oreumifancafe.domain.help;

import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Help {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = true)
    private String adminId;

    @Column(nullable = true)
    private String answer;

    @Column(nullable = false)
    @ColumnDefault("1")
    private int state;

    @CreatedDate
    @Column(nullable = true)
    private LocalDateTime answeredAt;

    @Column(nullable = false)
    private int helpType;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // 생성자
    @Builder
    public Help(String title, String content, String userId, int helpType) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.helpType = helpType;
    }

    public HelpResponse toResponse() {
        return HelpResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .userId(this.userId)
                .createdAt(this.createdAt)
                .adminId(this.adminId)
                .helpType(this.helpType)
                .state(this.state)
                .answer(this.answer)
                .answeredAt(this.answeredAt)
                .build();
    }
}
