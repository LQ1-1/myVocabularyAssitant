package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.service.EnglishVocabulary.EnglishVocabularyService;
import org.springframework.stereotype.Service;

@Service
public class WordPushService {
    private final EnglishVocabularyService englishVocabularyService;

    public WordPushService(EnglishVocabularyService englishVocabularyService) {
        this.englishVocabularyService = englishVocabularyService;
    }

    public EnglishVocabulary pickWordForUser(String uId) {
        EnglishVocabulary vocabulary = englishVocabularyService.learnNewEnglishVocabulary(uId);
        if (vocabulary != null) {
            return vocabulary;
        }
        return englishVocabularyService.getRandomEnglishVocabulary();
    }

    public EnglishVocabulary pickNextWord(String uId) {
        return pickWordForUser(uId);
    }
}
