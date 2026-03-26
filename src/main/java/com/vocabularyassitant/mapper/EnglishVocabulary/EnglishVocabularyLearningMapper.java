package com.vocabularyassitant.mapper.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabularyLearningRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EnglishVocabularyLearningMapper
{
    public Integer updateLearningRecord(EnglishVocabularyLearningRecords learningRecords);  //mainly use to update the latest learning date
    public Integer addLearningRecord(EnglishVocabularyLearningRecords learningRecords);
    public Integer deleteLearningRecords(@Param("vId") String vId, @Param("uId") String uId);

    public List<EnglishVocabulary> getAllLearningRecords(@Param("uId") String uId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    public Integer getAllLearningRecordsCount(@Param("uId") String uId);

    public EnglishVocabulary getHighPriorityVocabularyForReview(@Param("uId") String uId);
}
