package com.vocabularyassitant.mapper.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EnglishVocabularyMapper
{
    public EnglishVocabulary learnNewEnglishVocabulary(@Param("uId") String uId); //randomly pick a new unlearned word
    public EnglishVocabulary getRandomEnglishVocabulary();
}
