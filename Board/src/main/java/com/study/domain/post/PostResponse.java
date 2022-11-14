package com.study.domain.post;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class PostResponse {

    private Long id;                       // PK
    private String title;                  // 제목
    private String content;                // 내용
    private String writer;                 // 작성자
    private int viewCnt;                   // 조회 수
    private Boolean noticeYn;              // 공지글 여부
    private Boolean deleteYn;              // 삭제 여부
	
	private String createdDate;     // 생성일시
    private String modifiedDate;    // 최종 수정일시

	//private LocalDateTime createdDate;     // 생성일시
    //private LocalDateTime modifiedDate;    // 최종 수정일시

	//private Date createdDate;     // 생성일시
    //private Date modifiedDate;    // 최종 수정일시

    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public Boolean getNoticeYn() {
		return noticeYn;
	}
	public void setNoticeYn(Boolean noticeYn) {
		this.noticeYn = noticeYn;
	}
	public Boolean getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(Boolean deleteYn) {
		this.deleteYn = deleteYn;
	}



	/*  
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	*/
    
    
    
    
    
    
    
}
