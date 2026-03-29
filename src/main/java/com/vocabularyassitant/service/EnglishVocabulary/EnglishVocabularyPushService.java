package com.vocabularyassitant.service.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnglishVocabularyPushService
{
    @Autowired
    private EnglishVocabularyService englishVocabularyService;

    public EnglishVocabulary pickNextWord(String uId)
    {
        EnglishVocabulary vocabulary = englishVocabularyService.learnNewEnglishVocabulary(uId);
        if (vocabulary != null) {
            return vocabulary;
        }
        return englishVocabularyService.getRandomEnglishVocabulary();
    }
}
