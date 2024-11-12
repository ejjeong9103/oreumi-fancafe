package com.estsoft.oreumifancafe.domain.dto.admin;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String userId;
    private String nickname;
    private String address;
    private LocalDateTime createdAt;
    private int role;
    private int state;
    private String profileImageAddress;
}
