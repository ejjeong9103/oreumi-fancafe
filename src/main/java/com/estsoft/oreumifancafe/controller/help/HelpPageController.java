package com.estsoft.oreumifancafe.controller.help;

import com.estsoft.oreumifancafe.domain.dto.help.AddHelpRequest;
import com.estsoft.oreumifancafe.domain.dto.help.HelpResponse;
import com.estsoft.oreumifancafe.domain.help.Help;
import com.estsoft.oreumifancafe.domain.user.User;
import com.estsoft.oreumifancafe.service.help.HelpService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HelpPageController {
    private static final Logger log = LoggerFactory.getLogger(HelpPageController.class);
    private final HelpService service;
    private final HttpSession session;

    public HelpPageController(HelpService service, HttpSession session) {
        this.service = service;
        this.session = session;
    }

    // 문의 페이지
    @GetMapping("/help")
    public String helpForm(Model model) {
        return "qnaEditor";
    }


    // GET - 내가 쓴 질문 단건 조회
    @GetMapping("/help/question/{id}")
    public String findQuestionById(@PathVariable long id, Model model) {
        User currentUser = (User) session.getAttribute("user");
        Help question = service.findQuestionBy(id);

        if (!question.getUserId().equals(currentUser.getUserId()) && !currentUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("alertMessage", "해당 글을 볼 권한이 없습니다.");
            return "alert";
        }

        HelpResponse response = new HelpResponse(question);
        model.addAttribute("help", response);
        model.addAttribute("user", currentUser);
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
        // 임시 어드민
        question.setAdminId("admin");
        model.addAttribute("help", question);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("helpType", question.getHelpType());
        return "qnaEditor";
    }

    // PUT - 답변 작성하기
    @PutMapping("/answer/{id}")
    public String updateAnswer(@PathVariable Long id, @ModelAttribute AddHelpRequest request, Model model) {
        Help updateHelp = service.updateAnswer(id, request);
        HelpResponse response = new HelpResponse(updateHelp);
        model.addAttribute("question", response);
        return "myPage"; // 답변 작성 후 관리자 페이지로 넘어가도록 수정
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
    public String findHelpByUser(Model model) {
        User currentUser = (User) session.getAttribute("user");

        List<HelpResponse> inquiryList = service.findInquiryByUserId(currentUser.getUserId())
                .stream()
                .map(HelpResponse::new)
                .collect(Collectors.toList());
        model.addAttribute("inquiries", inquiryList);

        List<HelpResponse> declarationList = service.findDeclarationByUserId(currentUser.getUserId())
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

