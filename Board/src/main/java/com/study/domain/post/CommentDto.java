package com.study.domain.post;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@Data
public class CommentDto {
		 	 
    private Integer idx;

	private int boardIdx;

	private String content;

	private String writer;

    /** 페이징 정보 */
	//private PaginationInfo paginationInfo;

	/** 삭제 여부 */
	private String deleteYn;

	/** 등록일 */
	private String insertTime;

	/** 수정일 */
	private String updateTime;

	/** 삭제일 */
	private String deleteTime;

}
