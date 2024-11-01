package com.estsoft.oreumifancafe.service.user;

import com.estsoft.oreumifancafe.domain.dto.user.AddUserRequest;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public UserResponse saveUser(AddUserRequest addUserRequest) {
        // 유저 정보 확인
        // 빈값 확인
        addUserRequest.validate();

        // 아이디 중복 체크
        duplicateUserId(addUserRequest.getUserId());
        return null;
    }

    // 아이디 중복 체크
    public boolean isDuplicateUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public void duplicateUserId(String userId) {
        if (isDuplicateUserId(userId)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
    }
}
