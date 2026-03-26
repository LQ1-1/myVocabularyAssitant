package com.vocabularyassitant.service.EnglishVocabulary;

import com.vocabularyassitant.mapper.EnglishVocabulary.EnglishVocabularyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnglishVocabularyService
{
    @Autowired
    private EnglishVocabularyMapper englishVocabularyMapper;

}
