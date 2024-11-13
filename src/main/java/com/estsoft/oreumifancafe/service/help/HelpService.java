package com.estsoft.oreumifancafe.service.help;

import com.estsoft.oreumifancafe.domain.dto.help.AddHelpRequest;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.repository.help.HelpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Slf4j
@Service
public class HelpService {
    private static final int MY_PAGE_SIZE = 3;
    // String currentId = "id";  < 이 부분 나중에 일괄 수정해주세요.

    private final HelpRepository repository;

    public HelpService(HelpRepository repository) {
        this.repository = repository;
    }

    // 질문 저장
    public Help saveQuestion(AddHelpRequest helpRequest) {
        return repository.save(helpRequest.toEntity());
    }

    // 관리자 - 전체 질문 조회
    public List<Help> findAllQuestion() {
        return repository.findAll();
    }

    // 유저 - 작성자의 질문 전체 조회
    // 작성한 질문만 조회하기
    public List<Help> findAllQuestionByUser(String userId) {
        return repository.findByUserId(userId);
    }

    // 유저 - 내가 쓴 문의글 조회
    public List<Help> findInquiryByUserId(String userId) {
        return repository.findByUserIdAndHelpType(userId, 1);
    }

    // 유저 - 내가 쓴 신고글 조회
    public List<Help> findDeclarationByUserId(String userId) {
        return repository.findByUserIdAndHelpType(userId, 2);
    }


    // 질문 단건 조회
    public Help findQuestionBy(Long id) {
        Help help = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not fond id : " + id));
        return help;
    }

    // 답변 저장
    public Help updateAnswer(Long id, AddHelpRequest request) {

        Help help = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not fond id : " + id));
        help.setAnswer(request.getAnswer());
        help.setAdminId(request.getAdminId());
        help.setState(2);
        help.setAnsweredAt(LocalDateTime.now());
        return repository.save(help);
    }

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum - 1, pageSize);
    }

    // 마이 페이지 내 문의/신고내역 페이징
    public Page<HelpResponse> findByUserAndHelpType(User user, int pageNum, int helpType) {
        return repository.findHelpByUserIdAndHelpTypeOrderByIdDesc(user.getUserId(),  helpType, createPageRequest(pageNum, MY_PAGE_SIZE)).map(Help::toResponse);
    }
}
