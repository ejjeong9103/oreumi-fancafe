package com.estsoft.oreumifancafe.repository.user;

import com.estsoft.oreumifancafe.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);

    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickName);
    boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);
    Page<User> findUserByUserIdContaining(String userId, Pageable pageable);
    Page<User> findUserByNicknameContaining(String nickname, Pageable pageable);
}
