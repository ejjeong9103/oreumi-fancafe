package com.estsoft.oreumifancafe.service.auth;

import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호를 확인해주세요."));

        // 사용자 상태(state) 확인
        if (user.getState() == 0) {
            throw new DisabledException("탈퇴한 계정입니다.");
        }

        return user;
    }
}
