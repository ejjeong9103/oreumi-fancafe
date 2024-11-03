package com.estsoft.oreumifancafe.repository.auth;

import com.estsoft.oreumifancafe.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 사용자 ID 검색
    Optional<User> findByUser_id(String userId);

    // 사용자 닉네임 검색
    Optional<User> findByNickName(String nickName);

    // 사용자 이메일 검색
    Optional<User> findByEmail(String email);
}
