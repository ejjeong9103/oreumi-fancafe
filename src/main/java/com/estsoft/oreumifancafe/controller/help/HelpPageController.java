package com.estsoft.oreumifancafe.controller.help;

import com.estsoft.oreumifancafe.domain.dto.help.AddHelpRequest;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.service.help.HelpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HelpPageController {
    private final HelpService service;

    public HelpPageController(HelpService service) {
        this.service = service;
    }

    // 여기부터

    // 문의 페이지
    @GetMapping("/help")
    public String helpForm(Model model) {
        return "qnaEditor";
    }


    // GET - 내가 쓴 질문 단건 조회
    @GetMapping("/question/{id}")
    public String findQuestionById(@PathVariable long id, Model model) {
        Help question = service.findQuestionBy(id);
        HelpResponse response = new HelpResponse(question);
        model.addAttribute("help", response);
        return "qnadetail"; // 반환할 템플릿 이름
    }

    // GET - 내가 쓴 질문 전체 조회
//    @GetMapping("/question")
//    public String findAllQuestionsByUser(Model model) {
//        List<HelpResponse> questionList = service.findAllQuestionByUser().stream()
//                .map(HelpResponse::new)
//                .collect(Collectors.toList());
//        model.addAttribute("questions", questionList);
//        return "mypage"; // 반환할 템플릿 이름
//    }

    // GET - 답변 페이지
    @GetMapping("/answer/{id}")
    public String showAnswerEditor(@PathVariable long id, Model model) {
        Help question = service.findQuestionBy(id);
        model.addAttribute("help", question);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("helpType",question.getHelpType());
        return "qnaEditor";
    }

    // PUT - 답변 작성하기
    @PutMapping("/answer/{id}")
    public String updateAnswer(@PathVariable Long id, @ModelAttribute AddHelpRequest request, Model model) {
        Help updateHelp = service.updateAnswer(id, request);
        HelpResponse response = new HelpResponse(updateHelp);
        model.addAttribute("question", response);
        return "postEditor"; // 반환할 템플릿 이름
    }

    // 관리자 - 전체 질문 조회
    @GetMapping("/question/admin")
    public String findAllQuestion(Model model) {
        List<HelpResponse> questionList = service.findAllQuestion().stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("questions", questionList);
        return "board"; // 반환할 템플릿 이름
    }

    // 유저 - 내가 쓴 문의글/신고글 조회(하나의 페이지, 여러 게시판)
    @GetMapping("/myPage")
    public String findHelpByUser(@PathVariable String userId, Model model) {
        List<HelpResponse> inquiryList = service.findInquiryByUserId(userId)
                .stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("inquiries", inquiryList);

        List<HelpResponse> declarationList = service.findDeclarationByUserId(userId)
                .stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("declarations", declarationList);
        return "myPage"; // 반환할 템플릿 이름
    }

    // 유저 - 내가 쓴 신고글 조회
//    @GetMapping("/declaration/{userId}")
//    public String findDeclarationByUser(@PathVariable String userId, Model model) {
//        List<HelpResponse> declarationList = service.findDeclarationByUserId(userId)
//                .stream()
//                .map(HelpResponse::new)
//                .collect(Collectors.toList());
//        model.addAttribute("declarations", declarationList);
//        return "myPage"; // 반환할 템플릿 이름
//    }
}

