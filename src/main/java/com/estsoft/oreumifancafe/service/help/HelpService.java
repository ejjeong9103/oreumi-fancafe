package com.estsoft.oreumifancafe.service.help;

import com.estsoft.oreumifancafe.domain.dto.help.AddHelpRequest;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.repository.help.HelpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Slf4j
@Service
public class HelpService {
    private static final int MY_PAGE_SIZE = 3;
    // String currentId = "id";  < 이 부분 나중에 일괄 수정해주세요.


    //    private static final Logger log = LoggerFactory.getLogger(HelpService.class);
    private final HelpRepository repository;

    public HelpService(HelpRepository repository) {
        this.repository = repository;
    }

    // 글 작성자인지 검증
    private void authenticatedUser(Help help) {
        // 임시로 사용자 id를 "id"로 넣어뒀으니 스프링 시큐리티 적용 시 이 부분을 변경해주세요.
        String currentUserId = "id";
        if(!currentUserId.equals(help.getUserId()) && !isAdmin()) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
    }

    // principal로 현재 아이디 가져오기
//    public String getCurrentUserId(){
//
//    }

    // 관리자인지 확인
    // 현재 코드 실행을 위해 임시로 true로 설정해두었습니다.
    // 스프링 시큐리티 작업시 이 부분 채워넣기
    private boolean isAdmin(){
        return true;
    }

    // 질문 저장
    public Help saveQuestion(AddHelpRequest helpRequest) {
        authenticatedUser(helpRequest.toEntity());
        return repository.save(helpRequest.toEntity());
    }

    // 관리자 - 전체 질문 조회
    public List<Help> findAllQuestion(){
        if(!isAdmin()){
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }
        return repository.findAll();
    }

    // 유저 - 작성자의 질문 전체 조회
    // 작성한 질문만 조회하기
    public List<Help> findAllQuestionByUser() {
        // 임시 아이디로 넣어두었으니 스프링 시큐리티 사용시 수정해주세요.
        String currentUserId = "id";
        return repository.findByUserId(currentUserId);
    }

    // 유저 - 내가 쓴 문의글 조회
    // 문의글 1 신고글 2로 가정하고 코드를 넣었습니다. 확인 부탁드립니다.
    // 이 둘을 아예 하나의 코드로 합치는 것도 괜찮을 것 같습니다.
    public List<Help> findInquiryByUserId(String userId){
        String currentUserId = "id";
        if(!currentUserId.equals(userId) && !isAdmin()){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return repository.findByUserIdAndHelpType(userId,1);
    }

    // 유저 - 내가 쓴 신고글 조회
    public List<Help> findDeclarationByUserId(String userId){
        String currentUserId = "id";
        if(!currentUserId.equals(userId) && !isAdmin()){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return repository.findByUserIdAndHelpType(userId,2);
    }


    // 질문 단건 조회
    public Help findQuestionBy(Long id) {
        Help help = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not fond id : " + id));
        authenticatedUser(help);
        return help;
    }

    // 답변 저장
    public Help updateAnswer(Long id, AddHelpRequest request) {
        // 관리자인지 검증
        if(!isAdmin()){
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }

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
        return repository.findHelpByUserIdAndHelpType(user.getUserId(),  helpType, createPageRequest(pageNum, MY_PAGE_SIZE)).map(Help::toResponse);
    }
}
