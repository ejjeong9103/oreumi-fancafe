package com.estsoft.oreumifancafe.domain.help;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
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
        if (this.state == 0) {
            this.state = 1;
        }
    }

}
