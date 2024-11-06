package com.estsoft.oreumifancafe.domain.dto.admin;

import com.estsoft.oreumifancafe.domain.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private Long id;
    private String title;
    private User user;
    private int boardType;
    private String boardCategoryName;
    private int state;
}
