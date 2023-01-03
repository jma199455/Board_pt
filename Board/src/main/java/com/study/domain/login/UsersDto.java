
package com.study.domain.login;

import lombok.Data;

@Data
public class UsersDto {
    /*  
    private String id;  
    private String pw;  
    private String userName;
    */

    private int userSeq;
    private String id;  
    private String pw;  
    private String userName;
    private String createDate;
    private String loginDtm;

    private String genderCode;
    private String email;


}
