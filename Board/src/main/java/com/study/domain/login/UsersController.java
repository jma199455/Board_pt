package com.study.domain.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {
    
    @Autowired
    UsersService usersService;

    @GetMapping("/login")
    public String openLoginForm() {
        return "login/loginForm";
    }
    
    // 로그인
    @PostMapping("/loginCheck")
    public String loginCheck(HttpSession session, UsersDto UsersDto) throws Exception {

        String returnURL = "";

        if (session.getAttribute("login") != null) {
            session.removeAttribute("login");
        }

        UsersDto dto = usersService.getUser(UsersDto);
        if (dto != null) {
            session.setAttribute("login", dto);
            returnURL = "redirect:/post/list.do";
        } else {
            returnURL = "redirect:/login";
        }

        return returnURL;
        //return "/post/list.do"; 안되는 이유확인
    }

    // 로그아웃 하는 부분
    // post redirect 클래스 사용해서 보내보기
    @GetMapping("/logout.do")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 초기화
        return "redirect:/login"; // 로그아웃 후 로그인화면으로 이동
    }


}
