package com.estsoft.oreumifancafe.domain.user;

import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column
    private String userId;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int role;

    @Column(nullable = false)
    private int state;

    @Column(nullable = false)
    private String profileImageAddress;

    @Column(nullable = false)
    private String nickname;

    @Transient
    public UserResponse toUserResponse() {
        return new UserResponse(this.userId, this.nickname);
    }
}
