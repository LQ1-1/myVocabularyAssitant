package com.vocabularyassitant.controller.EnglishVocabularyController;


import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.EnglishVocabulary.EnglishVocabularyLearningService;
import com.vocabularyassitant.service.EnglishVocabulary.EnglishVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/api/EnglishVocabulary")
public class EnglishVocabularyController
{
    @Autowired
    private EnglishVocabularyService englishVocabularyService;
    @Autowired
    private EnglishVocabularyLearningService englishVocabularyLearningService;

    @PostMapping("updateLearningRecord")
    public Result updateLearningRecord(@RequestParam("vId") String vId, @RequestParam("uId") String uId, @RequestParam("lDate") LocalDate lDate, Jwt jwt)
    {
        return Result.success(englishVocabularyLearningService.updateLearningRecord(vId,uId,lDate,jwt));
    }

    @PostMapping("addLearningRecord")
    public Result addLearningRecord(@RequestParam("vId") String vId, @RequestParam("uId") String uId, @RequestParam("lDate") LocalDate lDate, Jwt jwt)
    {
        return Result.success(englishVocabularyLearningService.addLearningRecord(vId,uId,lDate,jwt));
    }

    @PostMapping("deleteLearningRecords")
    public Result deleteLearningRecords(@RequestParam("vId") String vId, @RequestParam("uId") String uId, Jwt jwt)
    {
        return Result.success(englishVocabularyLearningService.deleteLearningRecords(vId,uId,jwt));
    }

    @GetMapping("getAllLearningRecords")
    public Result getAllLearningRecords(@RequestParam("uId") String uId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset, Jwt jwt)
    {
        return englishVocabularyLearningService.getAllLearningRecords(uId,limit,offset,jwt);
    }
}
