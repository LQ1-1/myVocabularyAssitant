package com.vocabularyassitant.controller;

import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.CustomizedVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/CustomizedVocabulary")
public class CustomizedVocabularyController
{
    @Autowired
    private CustomizedVocabularyService customizedVocabularyService;

    @PostMapping("addCustomizedVocabularyNotebook")
    public Result addCustomizedVocabularyNotebook(@RequestParam("uId") String uId, @RequestParam("nName") String nName, @RequestParam("nDescription") String nDescription)
    {
        return Result.success(customizedVocabularyService.addCustomizedVocabularyNoteBook(uId, nName, nDescription));
    }

    @PostMapping("updateCustomizedVocabularyNoteBook")
    public Result updateCustomizedVocabularyNoteBook(@RequestParam("uId") String uId, @RequestParam("nId") String nId, @RequestParam("nName") String nName,  @RequestParam("nDescription") String nDescription, Jwt jwt)
    {
        return
    }
}
