package com.estsoft.oreumifancafe.domain.dto.user;

import com.estsoft.oreumifancafe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddUserRequest {
    private String userId;
    private String userPw;
    private String nickName;
    private String post;
    private String address;
    private String addressDetail;
    private String email;

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .userPw(this.userPw)
                .nickname(this.nickName)
                .address("(" + this.post + ")" + " " + this.address)
                .addressDetail(this.addressDetail)
                .email(this.email)
                .build();
    }
}
