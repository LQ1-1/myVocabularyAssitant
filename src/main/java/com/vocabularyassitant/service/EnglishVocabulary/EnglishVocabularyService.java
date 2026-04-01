package com.vocabularyassitant.service.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.mapper.EnglishVocabulary.EnglishVocabularyLearningMapper;
import com.vocabularyassitant.mapper.EnglishVocabulary.EnglishVocabularyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnglishVocabularyService
{
    @Autowired
    private EnglishVocabularyMapper englishVocabularyMapper;
    @Autowired
    private EnglishVocabularyLearningMapper englishVocabularyLearningMapper;

    public EnglishVocabulary learnNewEnglishVocabulary(String uId)
    {
        return englishVocabularyMapper.learnNewEnglishVocabulary(uId);
    }

    public EnglishVocabulary getRandomEnglishVocabulary()
    {
        return englishVocabularyMapper.getRandomEnglishVocabulary();
    }

    public EnglishVocabulary getHighPriorityVocabularyForReview(String uId)
    {
        return englishVocabularyLearningMapper.getHighPriorityVocabularyForReview(uId);
    }

    public EnglishVocabulary getRandomLearnedVocabulary(String uId)
    {
        return englishVocabularyLearningMapper.getRandomLearnedVocabulary(uId);
    }
}
