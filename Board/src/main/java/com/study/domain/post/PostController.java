package com.study.domain.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.paging.PagingResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }


    // 게시글 작성 페이지
    @GetMapping("/post/write.do")
    public String openPostWrite(@RequestParam(value = "id", required = false) final Integer id, Model model) {
        System.out.println("id =================> " + id);
        if (id != null) {
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);
        }
        return "post/write";
    }

    // 신규 게시글 생성
    @PostMapping("/post/save.do")
    public String savePost(final PostRequest params, Model model) {
        postService.savePost(params);
        MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    // 게시글 리스트 페이지
    @GetMapping("/post/list.do")
    public String openPostList(@ModelAttribute("params") SearchDto params,Model model) {
        PagingResponse<PostResponse> response = postService.findAllPost(params);

        model.addAttribute("response", response);
        return "post/list";
    }

    // 게시글 checkbox 삭제
    // ajax호출 시 @Controller 일경우 @ResponseBody 작성해줘야함
    @PostMapping("/post/deleteChk.do")
    @ResponseBody 
    public int deletePostList(@RequestParam int[] paramSeqs) throws Exception{
        return postService.delete(paramSeqs);
    }

    // 게시글 상세 페이지
    @GetMapping("/post/view.do")
    public String openPostView(@RequestParam final int id, Model model) {
        PostResponse post = postService.findPostById(id);
        model.addAttribute ("post", post);
        return "post/view";
    } 

    // 기존 게시글 수정
    @PostMapping("/post/update.do")
    public String updatePost(final PostRequest params, Model model) {
        postService.updatePost(params);
        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    // 게시글 삭제 form submit이기에 id하나만 필요하더라도 Command객체로 받기 가능
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam int id, Model model) {
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }



}