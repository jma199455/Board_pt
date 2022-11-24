package com.study.domain.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.common.dto.SearchDto;
import com.study.paging.Pagination;
import com.study.paging.PagingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     * @return Generated PK
     */
    @Transactional
    public int savePost(final PostRequest params) {
        postMapper.save(params);
        return params.getId();
    }

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostResponse findPostById(int id) {
        postMapper.updateCnt(id);

        PostResponse result = postMapper.findById(id);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        
        try {
            Date date = formater.parse(result.getCreatedDate());
            result.setCreatedDate(formater.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public int updatePost(final PostRequest params) {
        postMapper.update(params);
        return params.getId();
    }

    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
    public Map<String,Object> deletePost(Map<String,Object> queryParams) {
        
        postMapper.deleteById(queryParams);
        queryParams.remove("id");
        return queryParams;
    }

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    public PagingResponse<PostResponse> findAllPost(SearchDto params) {

        // 게시글 카운트
        int count = postMapper.count(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }       

        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);
        
        List<PostResponse> list = postMapper.findAll(params);

        // String 타입 createDate 따로 처리해줄 때 사용 (리스트 등록날짜 화면에 사용) 
        /*  
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd");
        for (PostResponse postResponse : list) {
            try {
                // 문자열을 Date 객체로 파싱
                Date date = formater.parse(postResponse.getCreatedDate());
                //System.out.println(date);
                //System.out.println(formater.format(date));
                postResponse.setCreatedDate(formater.format(date));
                //postResponse.setCreatedDate(formater.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        */

        /*  
        // return new PagingResponse<>(list, pagination); 생성자로 return하지 않고 set으로 넣어보기 ==> 가능
        PagingResponse<PostResponse> temp = new PagingResponse<>();
        temp.setList(list);
        temp.setPagination(pagination);
        return temp;
        */

        return new PagingResponse<>(list, pagination);
    }

    public int delete(int[] ids) {

        int result = 0;
        for (int id : ids) {
            System.out.println(id);
            result += postMapper.delete(id);
        }
        return result;

    }

}