package com.estsoft.oreumifancafe.controller.help;

import com.estsoft.oreumifancafe.domain.dto.help.AddHelpRequest;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.service.help.HelpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/help")
@RestController
public class HelpController {

    private final HelpService service;

    public HelpController(HelpService service) {
        this.service = service;
    }

    // POST - 질문 작성하기
    @PostMapping("/question")
    public ResponseEntity<HelpResponse> writeQuestion(@RequestBody AddHelpRequest request) {
        // 임시 사용자 id로 넣어두었으나 스프링 시큐리티 적용 시 글 작성자 아이디 받아올 수 있도록 하기
        request.setUserId("id");
        Help question = service.saveQuestion(request);
        HelpResponse response = new HelpResponse(question.getId(),question);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    // GET - 내가 쓴 질문 단건 조회
//    @GetMapping("/question/{id}")
//    public ResponseEntity<HelpResponse> findQuestionById(@PathVariable long id) {
//        Help question = service.findQuestionBy(id);
//        HelpResponse response = new HelpResponse(question);
//        return ResponseEntity.ok(response);
//    }

    // GET - 내가 쓴 질문 전체 조회
    // service에서 받아온 현재 유저의 아이디와 일치하는 질문 전체 조회
    @GetMapping("/question/all")
    public ResponseEntity<List<HelpResponse>> findAllQuestionsByUser() {
        List<HelpResponse> questionList = service.findAllQuestionByUser().stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questionList);
    }

    // PUT - 답변 작성하기
    @PutMapping("/answer/{id}")
    public ResponseEntity<HelpResponse> updateAnswer(@PathVariable Long id, @RequestBody AddHelpRequest request) {
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
    @GetMapping("/inquiry/{userId}")
    public ResponseEntity<List<HelpResponse>> findInquiryByUser(@PathVariable String userId) {
        List<HelpResponse> inquiryList = service.findInquiryByUserId(userId)
                .stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(inquiryList);
    }

    // 유저 - 내가 쓴 신고글 조회
    @GetMapping("/declaration/{userId}")
    public ResponseEntity<List<HelpResponse>> findDeclarationByUser(@PathVariable String userId) {
        List<HelpResponse> declarationList = service.findDeclarationByUserId(userId)
                .stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(declarationList);
    }

}
