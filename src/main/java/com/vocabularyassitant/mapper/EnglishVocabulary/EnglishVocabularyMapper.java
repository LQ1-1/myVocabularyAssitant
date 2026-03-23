package com.vocabularyassitant.mapper.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnglishVocabularyMapper
{
    public EnglishVocabulary learnNewEnglishVocabulary(); //randomly pick a new unlearned word
}
