package com.study.domain.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.common.dto.MessageDto;

@Controller
public class UsersController {
    
    @Autowired
    UsersService usersService;

    
    // loginForm.html 하나로만 error 문구 띄우기
    // @GetMapping("/login")
    @GetMapping({"/", "/login"})    // 2개 설정
    public String openLoginForm(@RequestParam(value = "error", required = false) String param, Model model) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 

        System.out.println("parammmmmmmmmmmmmm ====================> " + param);
        if (param != null) {
            System.out.println("들어감ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
            model.addAttribute("data", param);
        }

        return "login/loginForm";
    }

    /*  
    @GetMapping("/login")
    public String openLoginForm(@RequestParam(value = "error", required = false) String param) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 

        if (param != null) {
            System.out.println("openLoginForm 들어감");
            return "login/error";    
        }
        return "login/loginForm";
    }
    */

    // 로그인 체크
    @PostMapping("/loginCheck")
    public String loginCheck(HttpSession session, UsersDto UsersDto, RedirectAttributes redirect) throws Exception {    // RedirectAttributes redirect 리다이렉트 파라미터 보내기

        String returnURL = "";

        if (session.getAttribute("login") != null) {
        }
        session.removeAttribute("login");
        
        UsersDto dto = usersService.getUser(UsersDto);

        if (dto != null) {
            session.setAttribute("login", dto); 
            //returnURL = "redirect:/post/list.do";

            // 로그인시 로그인 테이블 등록
            int result = usersService.insert(dto);
            System.out.println("result ================> " + result);
            if (result > 0) {
                returnURL = "redirect:/post/list.do";
            }

        } else {
            String str = "failure";
            redirect.addAttribute("error",str); // 리다이렉트 파라미터 보내기
            returnURL = "redirect:/login";
        }

        return returnURL;
        //return "/post/list.do"; 안되는 이유확인
    }



    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }

    // redirect 클래스 사용해서 보내보기
    @GetMapping("/logout.do")
    public String logout(HttpSession session, Model model) {
        session.invalidate(); // 세션 초기화
        MessageDto message = new MessageDto("로그아웃이 완료되었습니다.", "/login", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    } 

    // 로그아웃 하는 부분 그냥 return "redirect:" 사용
    /*  
    @GetMapping("/logout.do")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 초기화
        return "redirect:/login"; // 로그아웃 후 로그인화면으로 이동
    }
    */
}
