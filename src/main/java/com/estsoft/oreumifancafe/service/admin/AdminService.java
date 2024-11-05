package com.estsoft.oreumifancafe.service.admin;

import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;

    // 유저 검색
    public UserResponse findUserById(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
    }
}
