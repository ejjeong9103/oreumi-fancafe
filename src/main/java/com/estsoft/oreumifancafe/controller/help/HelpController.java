package com.estsoft.oreumifancafe.controller.help;

import com.estsoft.oreumifancafe.domain.dto.help.AddHelpRequest;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.service.help.HelpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/help")
@RestController
public class HelpController {

    private final HelpService service;

    private final HttpSession session;

    public HelpController(HelpService service, HttpSession session) {
        this.service = service;
        this.session = session;
    }

    // POST - 질문 작성하기
    @PostMapping("/question")
    public ResponseEntity<HelpResponse> writeQuestion(@RequestBody AddHelpRequest request) {

        User currentUser = (User) session.getAttribute("user");
        request.setUserId(currentUser.getUserId());
        Help question = service.saveQuestion(request);
        HelpResponse response = new HelpResponse(question.getId(), question);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET - 내가 쓴 질문 전체 조회
    // service에서 받아온 현재 유저의 아이디와 일치하는 질문 전체 조회
    @GetMapping("/question/all")
    public ResponseEntity<List<HelpResponse>> findAllQuestionsByUser() {
        User currentUser = (User) session.getAttribute("user");
        List<HelpResponse> questionList = service.findAllQuestionByUser(currentUser.getUserId()).stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questionList);
    }

    // 관리자 권한은... 어떻게 하지?

    // PUT - 답변 작성하기 (관리자 권한)
    @PutMapping("/answer/{id}")
    public ResponseEntity<HelpResponse> updateAnswer(@PathVariable Long id, @RequestBody AddHelpRequest request) {
        User currentUser = (User) session.getAttribute("user");
        request.setAdminId(currentUser.getUserId());

        Help updateHelp = service.updateAnswer(id, request);
        HelpResponse response = new HelpResponse(updateHelp);
        return ResponseEntity.ok(response);
    }

    // 관리자 - 전체 질문 조회
    @GetMapping("/question/admin")
    public ResponseEntity<List<HelpResponse>> findAllQuestion() {
        List<HelpResponse> questionList = service.findAllQuestion().stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questionList);
    }

    // 유저 - 내가 쓴 문의글 조회
    @GetMapping("/inquiry")
    public ResponseEntity<List<HelpResponse>> findInquiryByUser() {
        User currentUser = (User) session.getAttribute("user");
        List<HelpResponse> inquiryList = service.findInquiryByUserId(currentUser.getUserId())
                .stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(inquiryList);
    }

    // 유저 - 내가 쓴 신고글 조회
    @GetMapping("/declaration")
    public ResponseEntity<List<HelpResponse>> findDeclarationByUser() {
        User currentUser = (User) session.getAttribute("user");
        List<HelpResponse> declarationList = service.findDeclarationByUserId(currentUser.getUserId())
                .stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(declarationList);
    }

}
