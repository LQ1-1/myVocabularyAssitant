package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabulary;
import com.vocabularyassitant.service.EnglishVocabulary.EnglishVocabularyService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordPushService {
    private final EnglishVocabularyService englishVocabularyService;

    public WordPushService(EnglishVocabularyService englishVocabularyService) {
        this.englishVocabularyService = englishVocabularyService;
    }

    public EnglishVocabulary pickWordForUser(String uId) {
        EnglishVocabulary newVocabulary = englishVocabularyService.learnNewEnglishVocabulary(uId);
        EnglishVocabulary reviewVocabulary = pickReviewWord(uId);

        if (newVocabulary == null) {
            return reviewVocabulary;
        }

        if (reviewVocabulary == null) {
            return newVocabulary;
        }

        return ThreadLocalRandom.current().nextBoolean() ? newVocabulary : reviewVocabulary;
    }

    public EnglishVocabulary pickNextWord(String uId) {
        return pickWordForUser(uId);
    }

    private EnglishVocabulary pickReviewWord(String uId) {
        EnglishVocabulary reviewVocabulary = englishVocabularyService.getHighPriorityVocabularyForReview(uId);
        if (reviewVocabulary != null) {
            return reviewVocabulary;
        }
        return englishVocabularyService.getRandomLearnedVocabulary(uId);
    }
}
