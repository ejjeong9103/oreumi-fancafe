package com.estsoft.oreumifancafe.service.user;

import com.estsoft.oreumifancafe.constans.user.Regx;
import com.estsoft.oreumifancafe.domain.dto.user.AddUserRequest;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public UserResponse saveUser(AddUserRequest addUserRequest) {
        // 유저 정보 확인
        // 빈값 확인
        addUserRequest.validate();

        // 아이디 중복 체크
        duplicateUserId(addUserRequest.getUserId());
        // 닉네임 중복 체크
        duplicateNickName(addUserRequest.getNickName());
        // 이메일 중복 체크
        duplicateEmail(addUserRequest.getEmail());
        // 비밀번호 정규식 체크
        validatePasswordRegex(addUserRequest.getUserPw());
        System.out.println(passwordEncoder.encode(addUserRequest.getUserPw()));

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

    // 닉네임 중복 체크
    public boolean isDuplicateNickName(String nickName) {
        return userRepository.existsByNickname(nickName);
    }

    public void duplicateNickName(String nickName) {
        if (isDuplicateNickName(nickName)) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
    }

    // 이메일 중복 체크
    public boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void duplicateEmail(String email) {
        if (isDuplicateEmail(email)) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
    }

    // 비밀번호 정규식 체크
    public void validatePasswordRegex(String password) {
        if (!Pattern.matches(Regx.PASSWORD_PATTERN.getRegex(), password)) {
            throw new IllegalArgumentException("비밀번호는 영소대문자, 숫자, 특수문자가 모두 포함되어야합니다.");
        }
    }
}
