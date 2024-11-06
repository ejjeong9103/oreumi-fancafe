package com.estsoft.oreumifancafe.domain.dto.admin;

import com.estsoft.oreumifancafe.domain.user.User;
import lombok.*;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
    private int boardType;
    private String boardCategoryName;
    private int state;
}
