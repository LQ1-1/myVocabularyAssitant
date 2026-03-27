package com.vocabularyassitant.mapper.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabularyNeworReviewCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.YearMonth;
import java.util.List;

@Mapper
public interface EnglishVocabularyNeworReviewCountMapper
{
    public List<EnglishVocabularyNeworReviewCount> getMonthlyEnglishVocabularyNeworReviewCount(@Param("uId") String uId, @Param("yearMonth")YearMonth yearMonth);
}
