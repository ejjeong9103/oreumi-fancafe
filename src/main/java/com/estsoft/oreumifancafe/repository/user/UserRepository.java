package com.estsoft.oreumifancafe.repository.user;

import com.estsoft.oreumifancafe.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserId(String userId);
    boolean existsByNickName(String nickName);
}
