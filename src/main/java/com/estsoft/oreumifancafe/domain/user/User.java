package com.estsoft.oreumifancafe.domain.user;

import com.estsoft.oreumifancafe.domain.dto.admin.UserInfoResponse;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class User implements UserDetails {
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "user_pw",nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String address;

    @Column(name = "address_detail", nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int role;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int state;

    @Column(name = "profile_image_address", nullable = false)
    private String profileImageAddress;

    @Column(nullable = false)
    private String nickname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == 1) {
            return List.of(new SimpleGrantedAuthority("guest")); // ROLE_GUEST : 준회원
        }
        else if (this.role == 2) {
            return List.of(new SimpleGrantedAuthority("user")); // ROLE_USER : 정회원
        }
        else if (this.role == 3) {
            return List.of(new SimpleGrantedAuthority("elite")); // ROLE_ELITE : 우수회원
        }
        else if (this.role == 4) {
            return List.of(new SimpleGrantedAuthority("celebrity")); // ROLE_CELEBRITY : 연예인
        }
        else if (this.role == 5) {
            return List.of(new SimpleGrantedAuthority("admin")); // ROLE_ADMIN : 관리자
        }

        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.userPw;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true; // true : 만료 X
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true; // true : 잠금 X
    }

    // 패스워드 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true : 만료 X
    }

    // 계정 사용 가능 여부
    @Override
    public boolean isEnabled() {
        return true; // true : 사용 가능
    }

    @Transient
    public UserResponse toUserResponse() {
        return new UserResponse(this.userId, this.nickname);
    }

    @Transient
    public UserInfoResponse toUserInfoResponse() {
        return UserInfoResponse.builder()
                .userId(this.userId)
                .userPw(this.userPw)
                .nickname(this.nickname)
                .address(this.address)
                .createdAt(this.createdAt)
                .role(this.role)
                .state(this.state)
                .profileImageAddress(this.profileImageAddress)
                .build();
    }

    // 사용자 상태 변경
    public void updateState(int state) {
        this.state = state;
    }

    // 사용자 등급 변경
    public void updateRole(int role) {
        this.role = role;
    }
}
