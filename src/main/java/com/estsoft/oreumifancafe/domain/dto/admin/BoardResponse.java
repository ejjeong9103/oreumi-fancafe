package com.estsoft.oreumifancafe.domain.dto.admin;

import com.estsoft.oreumifancafe.domain.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private User user;
    private int boardType;
    private String boardCategoryName;
    private int state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}
