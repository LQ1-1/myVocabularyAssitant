package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper
{
    public User getUserByUserId(@Param("uId") String uId);
    public Integer updateUserInfo(User user);
    public Integer addUser(User user);
}
