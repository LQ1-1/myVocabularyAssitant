package com.vocabularyassitant.mapper.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabularyLearningRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EnglishVocabularyLearningMapper
{
    public Integer updateLearningRecord(EnglishVocabularyLearningRecords learningRecords);
    public Integer addLearningRecord(EnglishVocabularyLearningRecords learningRecords);

    public List<EnglishVocabulary> getAllLearningRecords(@Param("uId") String uId);
    public EnglishVocabulary getHighPriorityVocabularyForReview(@Param("uId") String uId);
}
