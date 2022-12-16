package com.study.domain.login;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    
    public UsersDto usersLogin(UsersDto dto) throws Exception;




}
