package com.study.domain.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private final PostService postService;

    @Autowired
	private CommentService commentService;  // 확인 후 필요없으면 제거
    

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }


    // 게시글 작성 페이지
    // RequestParam은 get방식 쿼리스트링 받을 때 주로 사용!!!
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
    public String openPostList(@ModelAttribute("params") SearchDto params, Model model) {

        System.out.println("params =================================================================> " + params);
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
    public String openPostView(@ModelAttribute("params") CommentDto params, @RequestParam final int id, Model model) {

        //System.out.println("view 파라미터 확인 ========> " + params); // Get -> queryString 파라미터 확인 
        
        // 페이징 여기다가?... 아닌듯 지우기!
        /*  
        //CommentDto pageDto = commentService.getcommentlListPage(params);
        //model.addAttribute("pagination", pageDto);
        */

        PostResponse post = postService.findPostById(id);
        model.addAttribute ("post", post); // 게시판 상세 내용

        return "post/view";
    } 

    /*  
    // 기존 게시글 수정 from action으로 처리했을 때 사용 컨트롤러
    @PostMapping("/post/update.do")
    public String updatePost(final PostRequest params, Model model) {
        postService.updatePost(params);
        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }
    */

    // 게시글 수정 ajax 사용해보기
    // ajax로 넘겨주는 data가 json형식이기 때문에 @RequestBody로 꼭 받아줘야한다!!
    // ajax호출 컨트롤러이기 때문에 @ResponseBody를 꼭 작성해줘야한다!! 
    @PostMapping("/post/update.do")
    @ResponseBody
    public int updatePost(@RequestBody PostRequest params, Model model) {
        int result = postService.updatePost(params);
        return result;
    }



    /*  
    // 게시글 삭제 list.html 페이징 처리 전 컨트롤러
    // 게시글 삭제 form submit이기에 id하나만 필요하더라도 Command객체로 받기 가능하다!! check!!
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam int id, Model model) {
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }
    */

    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam Map<String,Object> queryParams, Model model) {
        Map<String,Object> queryParams2 = postService.deletePost(queryParams);
        //System.out.println("queryParams =======================> " + queryParams);

        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, queryParams2);
        return showMessageAndRedirect(message, model);
    }

}