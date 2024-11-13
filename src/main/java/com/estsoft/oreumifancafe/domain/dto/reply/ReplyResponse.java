package com.estsoft.oreumifancafe.domain.dto.reply;

import com.estsoft.oreumifancafe.domain.dto.admin.BoardResponse;
import com.estsoft.oreumifancafe.domain.reply.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyResponse {
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int hierarchy;
    private int orders;
    private int group;
    private String userNickname;
    private BoardResponse boardResponse;

    @Builder
    public ReplyResponse(String content, LocalDateTime createdAt, LocalDateTime updatedAt, int hierarchy, int orders, int group, String userNickname) {
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.hierarchy = hierarchy;
        this.orders = orders;
        this.group = group;
        this.userNickname = userNickname;
    }
}
