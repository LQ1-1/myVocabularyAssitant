package com.vocabularyassitant.service.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.mapper.EnglishVocabulary.EnglishVocabularyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnglishVocabularyService
{
    @Autowired
    private EnglishVocabularyMapper englishVocabularyMapper;

    public EnglishVocabulary learnNewEnglishVocabulary(String uId)
    {
        return englishVocabularyMapper.learnNewEnglishVocabulary(uId);
    }

    public EnglishVocabulary getRandomEnglishVocabulary()
    {
        return englishVocabularyMapper.getRandomEnglishVocabulary();
    }
}
