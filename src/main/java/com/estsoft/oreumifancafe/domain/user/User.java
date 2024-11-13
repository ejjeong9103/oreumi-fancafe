package com.estsoft.oreumifancafe.domain.user;

import com.estsoft.oreumifancafe.domain.dto.admin.UserInfoResponse;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
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

    // 역할을 저장할 컬렉션 필드 추가
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new HashSet<>();

    @Column(name = "state")
    private int state;

    @Column(name = "profileImageAddress")
    private String profileImageAddress;

    @Column(nullable = false)
    private String nickname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
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
                .nickname(this.nickname)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .createdAt(this.createdAt)
                .email(this.email)
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
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateUserPw(String userPw) {
        this.userPw = userPw;
    }

    public void updateAddress(String address, String addressDetail) {
        this.address = address;
        this.addressDetail = addressDetail;
    }

    public void deleteUser() {
        this.state = 0;
    }
}
