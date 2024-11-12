package com.estsoft.oreumifancafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        // 메인 페이지 게시판 목록 불러오기
        // 추후 리펙토링
        // 게시판 type의 테이블을 만들어 해당 테이블을 조회해서
        // nav에서 사용
        return "index";
    }
}
