package com.estsoft.oreumifancafe.domain.dto.help;

import com.estsoft.oreumifancafe.domain.help.Help;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddHelpRequest {
    private String title;
    private String content;
    private String userId;
    private int helpType;
    private LocalDateTime createdAt;
    private String adminId;
    private String answer;
    private LocalDateTime answeredAt;

    public Help toEntity(){
        return Help.builder()
                .title(this.title)
                .content(this.content)
                .userId(this.userId)
                .helpType(this.helpType)
                .build();
    }
}
