package com.estsoft.oreumifancafe.domain.dto.help;

import com.estsoft.oreumifancafe.domain.help.Help;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class HelpResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String userId;
    private String adminId;
    private String answer;
    private int state;
    private LocalDateTime answeredAt;
    private int helpType;

    public HelpResponse(Help help) {
        this.id = help.getId();
        this.title = help.getTitle();
        this.content = help.getContent();
        this.createdAt = help.getCreatedAt();
        this.userId = help.getUserId();
        this.state = help.getState();
        this.helpType = help.getHelpType();

        this.answer = help.getAnswer();
        this.answeredAt = help.getAnsweredAt();
        this.adminId = help.getAdminId();

    }

    public HelpResponse(Long id, Help help) {
        this.id = help.getId();
        this.title = help.getTitle();
        this.content = help.getContent();
        this.createdAt = help.getCreatedAt();
        this.userId = help.getUserId();
        this.state = help.getState();
        this.helpType = help.getHelpType();

        this.answer = help.getAnswer();
        this.answeredAt = help.getAnsweredAt();
        this.adminId = help.getAdminId();
    }

}
