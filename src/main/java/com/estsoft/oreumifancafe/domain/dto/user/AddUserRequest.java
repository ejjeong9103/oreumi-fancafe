package com.estsoft.oreumifancafe.domain.dto.user;

import com.estsoft.oreumifancafe.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;

@Getter
@AllArgsConstructor
public class AddUserRequest {
    private String userId;
    private String userPw;
    private String nickname;
    private String post;
    private String address;
    private String addressDetail;
    private String email;

    // 기본 회원 toEntity
    public User toEntity(String encodedPassword) {
        return User.builder()
                .userId(this.userId)
                .userPw(encodedPassword)
                .nickname(this.nickname)
                .address("(" + this.post + ")" + " " + this.address)
                .addressDetail(this.addressDetail == null ? "" : this.addressDetail)
                .email(this.email)
                .state(1)
                .roles(new HashSet<>())
                .build();
    }

    public void validate()  {
        loginValidate();
        if (this.nickname == null || this.nickname.isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수 입력값입니다.");
        }
        if (this.post == null || this.post.isEmpty()) {
            throw new IllegalArgumentException("우편번호는 필수 입력값입니다.");
        }
        if (this.address == null || this.address.isEmpty()) {
            throw new IllegalArgumentException("주소는 필수 입력값입니다.");
        }
        if (this.email == null || this.email.isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수 입력값입니다.");
        }
    }

    public void loginValidate() {
        if (this.userId == null || this.userId.isEmpty()) {
            throw new IllegalArgumentException("아이디는 필수 입력값입니다.");
        }
        if (this.userPw == null || this.userPw.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력값입니다.");
        }
    }
}
