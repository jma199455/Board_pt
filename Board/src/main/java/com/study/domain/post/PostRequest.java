package com.study.domain.post;

import lombok.Data;

@Data
public class PostRequest {


	private int id;             // PK
    private String title;        // 제목
    private String content;      // 내용
    private String writer;       // 작성자
    private Boolean noticeYn;    // 공지글 여부
    
    
	
}
