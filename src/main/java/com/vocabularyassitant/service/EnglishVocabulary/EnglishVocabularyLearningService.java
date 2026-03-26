package com.vocabularyassitant.service.EnglishVocabulary;

import com.vocabularyassitant.entity.EnglishVocabulary.EnglishVocabularyLearningRecords;
import com.vocabularyassitant.mapper.EnglishVocabulary.EnglishVocabularyLearningMapper;
import com.vocabularyassitant.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EnglishVocabularyLearningService
{
    @Autowired
    private EnglishVocabularyLearningMapper englishVocabularyLearningMapper;

    public Integer updateLearningRecord(String vId, String uId, LocalDate lDate, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            EnglishVocabularyLearningRecords records=new EnglishVocabularyLearningRecords();
            records.setvId(vId);
            records.setuId(uId);
            records.setlDate(lDate);
            return englishVocabularyLearningMapper.updateLearningRecord(records);
        }
    }

    public Integer addLearningRecord(String vId, String uId, LocalDate lDate, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            EnglishVocabularyLearningRecords records=new EnglishVocabularyLearningRecords();
            records.setvId(vId);
            records.setuId(uId);
            records.setlDate(lDate);
            return englishVocabularyLearningMapper.addLearningRecord(records);
        }
    }

    public Integer deleteLearningRecords(String vId, String uId, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            return englishVocabularyLearningMapper.deleteLearningRecords(vId,uId);
        }
    }

    public Result getAllLearningRecords(String uId, Integer limit, Integer offset, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            return Result.success(englishVocabularyLearningMapper.getAllLearningRecords(uId,limit,offset), englishVocabularyLearningMapper.getAllLearningRecordsCount(uId));
        }
    }


}
