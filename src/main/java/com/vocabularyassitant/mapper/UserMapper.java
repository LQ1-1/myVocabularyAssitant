package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper
{
    public User getUserByUserId(String uId);
}
