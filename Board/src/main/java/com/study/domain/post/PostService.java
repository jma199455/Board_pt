package com.study.domain.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public int deletePost(int id) {
        int result = 0;
        result = postMapper.deleteById(id);
        return result;
    }

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    public List<PostResponse> findAllPost() {

        List<PostResponse> posts = postMapper.findAll();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd");
        for (PostResponse postResponse : posts) {
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

        return posts;
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