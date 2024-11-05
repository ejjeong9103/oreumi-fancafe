package com.estsoft.oreumifancafe.service.admin;

import com.estsoft.oreumifancafe.domain.dto.admin.UserInfoResponse;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.dto.user.UserResponse;
import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.exceptions.UserNotFoundException;
import com.estsoft.oreumifancafe.repository.help.HelpRepository;
import com.estsoft.oreumifancafe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final HelpRepository helpRepository;

    // ID로 사용자 조회
    public UserResponse findUserById(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .toUserResponse();
    }

    // 신고 및 문의 전체 조회
    public List<HelpResponse> getAllHelp(String userId) {
        return helpRepository.findByUserId(userId).stream()
                .map(HelpResponse::new)
                .toList();
    }

    // 신고 및 문의 페이지 카운트
    public Long helpPageCount(String userId) {
        List<HelpResponse> helpList = getAllHelp(userId);

        return (long) helpList.size();
    }

    // 게시글 전체 조회

    // 특정 게시판 게시글 조회

    // 모든 사용자 조회
    
    // 사용자 정보 조회
    public UserInfoResponse userInfo(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .toUserInfoResponse();
    }
}
